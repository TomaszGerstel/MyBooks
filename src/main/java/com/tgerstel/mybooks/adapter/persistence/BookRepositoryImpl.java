package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.BookRepository;
import com.tgerstel.mybooks.domain.model.UserBook;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private BookSpringRepository bookSpringRepository;
    private UserBookSpringRepository userBookSpringRepository;
    private UserSpringRepository userSpringRepository;

    @Override
    public boolean existsById(final String id) {
        return bookSpringRepository.existsById(id);
    }

    @Override
    public void save(final ExternalBook book) {
        final var bookEntity = new BookEntity(book);
        bookSpringRepository.save(bookEntity);
    }

    @Override
    public void deleteById(final String id) {
        bookSpringRepository.deleteById(id);
    }

    @Override
    public List<UserBook> findBooksByUsername(final String username) {
        return userBookSpringRepository.findByUserUsername(username)
                .stream()
                .map(UserBookEntity::toDomain)
                .toList();
    }

    @Override
    public void addBookToUserLibrary(final String bookId, final String username) {
        final var userEntity = userSpringRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        final var bookEntity = bookSpringRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
        final var userBook = new UserBookEntity(userEntity, bookEntity, false);
        userBookSpringRepository.save(userBook);
    }

    @Override
    @Transactional
    public void removeBookFromUserLibrary(final String bookId, final String username) {
        userBookSpringRepository.deleteByBookIdAndUserUsername(bookId, username);
    }

    @Override
    public void changeBookReadStatus(final String bookId, final String username) {
        final var userBook = userBookSpringRepository.findByBookIdAndUserUsername(bookId, username)
                .orElseThrow(() -> new IllegalArgumentException("UserBook not found for bookId: " + bookId + " and username: " + username));
        userBook.setRead(!userBook.isRead());
        userBookSpringRepository.save(userBook);
    }

}
