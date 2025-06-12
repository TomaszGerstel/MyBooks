package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.UserBook;
import com.tgerstel.mybooks.domain.model.ExternalBook;

import java.util.List;

public interface BookRepository {
    boolean existsById(String id);
    void save(ExternalBook book);
    void deleteById(String id);
    List<UserBook> findBooksByUsername(String username);
    void addBookToUserLibrary(String bookId, String username);
    void removeBookFromUserLibrary(String bookId, String username);
    void changeBookReadStatus(String bookId, String username);
}
