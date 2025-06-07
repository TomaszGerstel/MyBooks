package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String id);
//    List<Book> findAllForUser(String userId);
    void save(Book book);
    void deleteById(String id);
}
