package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;

import java.util.List;

public interface BookService {
    PaginatedBooks searchBooks(String query, int page, int size);
    void saveBookLocally(ExternalBook book);
    void addBookToUserLibrary(String bookId, Long userId);
    List<Book> getUserLibrary(Long userId);
    void removeBookFromUserLibrary(String bookId, Long userId);
}
