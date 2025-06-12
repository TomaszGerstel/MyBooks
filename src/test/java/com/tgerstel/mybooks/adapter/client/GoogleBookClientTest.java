package com.tgerstel.mybooks.adapter.client;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GoogleBookClientTest {

    @Mock
    private RestTemplate restTemplate;

    private static final String GOOGLE_BOOKS_API_URL = "http://example.com/api";

    private GoogleBookClient googleBookClient;

    @BeforeEach
    void setUp() {
        googleBookClient = new GoogleBookClient(restTemplate, GOOGLE_BOOKS_API_URL);
    }

    @Test
    @DisplayName("Should return paginated books when API responds successfully")
    void findBooksTest_1() {
        // given
        String query = "test";
        int startIndex = 0;
        int maxResults = 10;
        String apiUrl = "http://example.com/api?q=test&startIndex=0&maxResults=10";
        GoogleBooksResponse mockResponse = new GoogleBooksResponse(2, List.of(
                createGoogleBookItem("1", "Title 1", "Author 1"),
                createGoogleBookItem("2", "Title 2", "Author 2")
        ));
        when(restTemplate.getForObject(apiUrl, GoogleBooksResponse.class)).thenReturn(mockResponse);

        // when
        PaginatedBooks result = googleBookClient.findBooks(query, startIndex, maxResults);

        // then
        assertEquals(2, result.books().size());
        assertFalse(result.hasNextPage());
        verify(restTemplate, times(1)).getForObject(apiUrl, GoogleBooksResponse.class);
    }

    @Test
    @DisplayName("Should return empty paginated books when API responds with null items")
    void findBooksTest_2() {
        // given
        String query = "test";
        int startIndex = 0;
        int maxResults = 10;
        String apiUrl = "http://example.com/api?q=test&startIndex=0&maxResults=10";
        GoogleBooksResponse mockResponse = new GoogleBooksResponse(0, null);
        when(restTemplate.getForObject(apiUrl, GoogleBooksResponse.class)).thenReturn(mockResponse);

        // when
        PaginatedBooks result = googleBookClient.findBooks(query, startIndex, maxResults);

        // then
        assertEquals(0, result.books().size());
        assertFalse(result.hasNextPage());
        verify(restTemplate, times(1)).getForObject(apiUrl, GoogleBooksResponse.class);
    }

    @Test
    @DisplayName("Should return external book when API responds successfully")
    void findBookByIdTest_1() {
        // given
        String bookId = "1";
        String apiUrl = "http://example.com/api/1";
        GoogleBookItem mockItem = createGoogleBookItem(bookId, "Title 1", "Author 1");
        when(restTemplate.getForObject(apiUrl, GoogleBookItem.class)).thenReturn(mockItem);

        // when
        ExternalBook result = googleBookClient.findBookById(bookId);

        // then
        assertNotNull(result);
        assertEquals(bookId, result.id());
        assertEquals("Title 1", result.title());
        verify(restTemplate, times(1)).getForObject(apiUrl, GoogleBookItem.class);
    }

    private GoogleBookItem createGoogleBookItem(String id, String title, String author) {
        var volumeInfo = new GoogleBookItem.VolumeInfo();
        var imageLinks = new GoogleBookItem.ImageLinks();
        imageLinks.setSmallThumbnail("http://example.com/thumbnail.jpg");
        volumeInfo.setTitle(title);
        volumeInfo.setAuthors(List.of(author));
        volumeInfo.setPublishedDate("2023-01-01");
        volumeInfo.setDescription("Description");
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setCategories(List.of("Category"));

        var item = new GoogleBookItem();
        item.setId(id);
        item.setVolumeInfo(volumeInfo);
        return item;
    }
}