package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.ExternalBook;

import java.util.List;

public interface BookService {
    List<ExternalBook> searchBooks(String query);
    void saveBookLocally(ExternalBook book);
    void addBookToUserLibrary(String bookId, Long userId);
    List<Book> getUserLibrary(Long userId);
    void removeBookFromUserLibrary(String bookId, Long userId);
}
