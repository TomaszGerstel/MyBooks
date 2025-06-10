package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

//    private final BookRepository bookRepository;
    private final ExternalBookProvider externalBookProvider;

    @Override
    public PaginatedBooks searchBooks(String query, int page, int size) {
        return externalBookProvider.findBooks(query, page, size);
    }

    @Override
    public void saveBookLocally(ExternalBook book) {

    }

    @Override
    public void addBookToUserLibrary(String bookId, Long userId) {

    }

    @Override
    public List<Book> getUserLibrary(Long userId) {
        return List.of();
    }

    @Override
    public void removeBookFromUserLibrary(String bookId, Long userId) {

    }
}
