package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.ExternalBook;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String id);
    void save(ExternalBook book);
    void deleteById(String id);
}
