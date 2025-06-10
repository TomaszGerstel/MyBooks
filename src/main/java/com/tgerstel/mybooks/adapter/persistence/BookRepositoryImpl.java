package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.BookRepository;
import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private BookSpringRepository bookSpringRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookSpringRepository.findById(id).map(BookEntity::toDomain);
    }

    @Override
    public void save(ExternalBook book) {
        BookEntity bookEntity = new BookEntity(book);
        bookSpringRepository.save(bookEntity);
    }

    @Override
    public void deleteById(String id) {
        bookSpringRepository.deleteById(id);
    }

}
