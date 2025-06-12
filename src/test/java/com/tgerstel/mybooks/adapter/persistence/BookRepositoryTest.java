package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.UserBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Mock
    private BookSpringRepository bookSpringRepository;

    @Mock
    private UserBookSpringRepository userBookSpringRepository;

    @Mock
    private UserSpringRepository userSpringRepository;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Test
    @DisplayName("Should return true if user book exists")
    void existsByIdTest_1() {
        // given
        String bookId = "1";
        when(bookSpringRepository.existsById(bookId)).thenReturn(true);

        // when
        boolean exists = bookRepository.existsById(bookId);

        // then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false if user book does not exist")
    void existsByIdTest_2() {
        // given
        String bookId = "999";
        when(bookSpringRepository.existsById(bookId)).thenReturn(false);

        // when
        boolean exists = bookRepository.existsById(bookId);

        // then
        assertFalse(exists);
    }

    @Test
    @DisplayName("Should save user book")
    void saveTest() {
        // given
        ExternalBook externalBook = createExternalBook("1", "Sample Book", "Sample Author");

        // when
        bookRepository.save(externalBook);

        // then
        verify(bookSpringRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    @DisplayName("Should delete book")
    void deleteByIdTest() {
        // given
        String bookId = "1";

        // when
        bookRepository.deleteById(bookId);

        // then
        verify(bookSpringRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Should save user book")
    void findBookBuUsernameTest() {
        // given
        String book1Id = "1";
        String book2Id = "2";
        String username = "user";
        List<UserBookEntity> books = List.of(createUserBookEntity(book1Id, username, false),
                                             createUserBookEntity(book2Id, username, true));
        when(userBookSpringRepository.findByUserUsername(username)).thenReturn(books);

        // when
        List<UserBook> result = bookRepository.findBooksByUsername(username);

        // then
        assertEquals(2, result.size());
        assertEquals(book1Id, result.getFirst().id());
        assertEquals(book2Id, result.getLast().id());
        assertFalse(result.getFirst().isRead());
        assertTrue(result.getLast().isRead());
        verify(userBookSpringRepository, times(1)).findByUserUsername(username);
    }

    @Test
    @DisplayName("Should add book to user library if user and book exist")
    void addBookToUserLibraryTest() {
        // given
        String bookId = "1";
        String username = "nonExistentUser";
        UserEntity userEntity = createUserEntity(username);
        BookEntity bookEntity = createBookEntity(bookId);
        when(userSpringRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(bookSpringRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // when
        bookRepository.addBookToUserLibrary(bookId, username);

        // then
        verify(userBookSpringRepository, times(1)).save(any(UserBookEntity.class));
    }

    @Test
    @DisplayName("Should remove book from user library")
    void removeBookFromUserLibraryTest() {
        // given
        String bookId = "1";
        String username = "user";

        // when
        bookRepository.removeBookFromUserLibrary(bookId, username);

        // then
        verify(userBookSpringRepository, times(1)).deleteByBookIdAndUserUsername(bookId, username);
    }

    @Test
    @DisplayName("Should change book read status")
    void changeBookReadStatusTest() {
        // given
        String bookId = "1";
        String username = "user";
        UserBookEntity userBookEntity = createUserBookEntity(bookId, username, false);
        when(userBookSpringRepository.findByBookIdAndUserUsername(bookId, username)).thenReturn(Optional.of(userBookEntity));

        // when
        bookRepository.changeBookReadStatus(bookId, username);

        // then
        assertTrue(userBookEntity.isRead());
        verify(userBookSpringRepository).save(userBookEntity);
    }

    UserBookEntity createUserBookEntity(String bookId, String username, boolean isRead) {
        UserEntity userEntity = createUserEntity(username);
        BookEntity bookEntity = createBookEntity(bookId);
        UserBookEntity userBookEntity = new UserBookEntity();
        userBookEntity.setBook(bookEntity);
        userBookEntity.setUser(userEntity);
        userBookEntity.setRead(isRead);
        return userBookEntity;
    }

    UserEntity createUserEntity(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        return userEntity;
    }

    BookEntity createBookEntity(String bookId) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookId);
        bookEntity.setTitle("Sample Book Title");
        bookEntity.setAuthors("Sample Author");
        bookEntity.setCoverImageUrl("http://example.com/sample-cover.jpg");
        bookEntity.setPublicationDate("2023-01-01");
        return bookEntity;
    }

    ExternalBook createExternalBook(String id, String title, String author) {
        return new ExternalBook(id, title, author, "2023-01-01", "Description", "http://example.com/image.jpg", "Category");
    }
}
