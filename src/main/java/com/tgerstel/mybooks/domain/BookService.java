package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import com.tgerstel.mybooks.domain.model.UserBook;

import java.util.List;

public interface BookService {
    PaginatedBooks searchBooks(String query, int page, int size);
    void saveBookLocally(ExternalBook book, String username);
    void removeBookFromUserLibrary(String bookId, String username);
    void changeBookReadStatus(String bookId, String username);
    List<UserBook> getUserLibrary(String username);
}
