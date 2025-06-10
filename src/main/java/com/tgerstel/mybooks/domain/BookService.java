package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;

public interface BookService {
    PaginatedBooks searchBooks(String query, int page, int size);
    void saveBookLocally(ExternalBook book, String username);
    void removeBookFromUserLibrary(String bookId, String username);
}
