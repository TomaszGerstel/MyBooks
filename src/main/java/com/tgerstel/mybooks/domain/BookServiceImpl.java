package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ExternalBookProvider externalBookProvider;
    private final UserRepository userRepository;

    @Override
    public PaginatedBooks searchBooks(String query, int page, int size) {
        return externalBookProvider.findBooks(query, page, size);
    }

    @Override
    public void saveBookLocally(ExternalBook externalBook, String username) {
        bookRepository.findById(externalBook.id())
                .ifPresentOrElse(
                        book -> {
                            userRepository.addBookToUserLibrary(externalBook, username);
                        },
                        () -> {
                            bookRepository.save(externalBook);
                            userRepository.addBookToUserLibrary(externalBook, username);
                        }
                );
    }

    @Override
    public void removeBookFromUserLibrary(String bookId,String username) {
        bookRepository.findById(bookId)
                .ifPresent(book -> {
                    userRepository.removeBookFromUserLibrary(book, username);
                });
    }
}
