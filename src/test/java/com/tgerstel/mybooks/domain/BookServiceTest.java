package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import com.tgerstel.mybooks.domain.model.UserBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ExternalBookProvider externalBookProvider;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Search books should return paginated books for valid query")
    void searchBooksTest() {
        // given
        String query = "test";
        int page = 0;
        int size = 10;
        var expectedBooks = new PaginatedBooks(createExternalBooks(3), 3, false);
        when(externalBookProvider.findBooks(query, page, size)).thenReturn(expectedBooks);

        // when
        PaginatedBooks result = bookService.searchBooks(query, page, size);

        // then
        verify(externalBookProvider).findBooks(query, page, size);

        assertEquals(result.books().size(), expectedBooks.books().size());
        assertFalse(result.hasNextPage());
        result.books().forEach(book -> {
            var expectedBook = expectedBooks.books().stream()
                    .filter(b -> b.id().equals(book.id()))
                    .findFirst()
                    .orElse(null);
            assertEquals(expectedBook, book);
        });
    }

    @Test
    @DisplayName("Should save book if it does not exist")
    void saveBookLocallyTest_1() {
        // given
        var externalBook = createExternalBook("1", "Title", "Author");
        String username = "user";
        when(bookRepository.existsById(externalBook.id())).thenReturn(false);

        // when
        bookService.saveBookLocally(externalBook, username);

        // then
        verify(bookRepository).save(externalBook);
        verify(bookRepository).addBookToUserLibrary(externalBook.id(), username);
    }

    @Test
    @DisplayName("Should not save book if it already exists")
    void saveBookLocallyTest_2() {
        // given
        var externalBook = createExternalBook("1", "Title", "Author");
        String username = "user";
        when(bookRepository.existsById(externalBook.id())).thenReturn(true);

        // when
        bookService.saveBookLocally(externalBook, username);

        // then
        verify(bookRepository, never()).save(externalBook);
        verify(bookRepository).addBookToUserLibrary(externalBook.id(), username);
    }

    @Test
    @DisplayName("Should remove book from user library for valid book and user")
    void removeBookTest() {
        // given
        String bookId = "1";
        String username = "user";

        // when
        bookService.removeBookFromUserLibrary(bookId, username);

        // then
        verify(bookRepository).removeBookFromUserLibrary(bookId, username);
    }

    @Test
    @DisplayName("Should change book read status for valid book and user")
    void changeBookReadStatusTest() {
        // given
        String bookId = "1";
        String username = "user";

        // when
        bookService.changeBookReadStatus(bookId, username);

        // then
        verify(bookRepository).changeBookReadStatus(bookId, username);
    }

    @Test
    @DisplayName("Get user library should return list of user books")
    void getUserLibraryTest() {
        // given
        String username = "user";
        var userBooks = createUserBooks(3);
        when(bookRepository.findBooksByUsername(username)).thenReturn(userBooks);

        // when
        var result = bookService.getUserLibrary(username);

        // then
        assertEquals(userBooks.size(), result.size());
        result.forEach(book -> {
            var expectedBook = userBooks.stream()
                    .filter(b -> b.id().equals(book.id()))
                    .findFirst()
                    .orElse(null);
            assertEquals(expectedBook, book);
        });
    }

    List<ExternalBook> createExternalBooks(int count) {
        var externalBooks = new ArrayList<ExternalBook>();
        for (int i = 1; i <= count; i++) {
            externalBooks.add(createExternalBook(String.valueOf(i), "Title " + i, "Author " + i));
        }
        return externalBooks;
    }

    ExternalBook createExternalBook(String id, String title, String author) {
        return new ExternalBook(id, title, author, "2023-01-01", "Description", "http://example.com/image.jpg", "Category");
    }

    List<UserBook> createUserBooks(int count) {
        var userBooks = new ArrayList<UserBook>();
        for (int i = 1; i <= count; i++) {
            userBooks.add(new UserBook(String.valueOf(i), "Title " + i, "Author " + i, "2023-01-01", "http://example.com/image.jpg", false));
        }
        return userBooks;
    }


}