package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import com.tgerstel.mybooks.domain.model.UserBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ExternalBookProvider externalBookProvider;

    @Override
    public PaginatedBooks searchBooks(final String query, final int page, final int size) {
        return externalBookProvider.findBooks(query, page, size);
    }

    @Override
    @Transactional
    public void saveBookLocally(final ExternalBook externalBook, final String username) {
        var bookId = externalBook.id();
        if (!bookRepository.existsById(bookId)) {
            bookRepository.save(externalBook);
        }
        bookRepository.addBookToUserLibrary(bookId, username);
    }

    @Override
    public void removeBookFromUserLibrary(final String bookId, final String username) {
        bookRepository.removeBookFromUserLibrary(bookId, username);
    }

    @Override
    public void changeBookReadStatus(String bookId, String username) {
        bookRepository.changeBookReadStatus(bookId, username);
    }

    @Override
    public List<UserBook> getUserLibrary(String username) {
        return bookRepository.findBooksByUsername(username);
    }


}
