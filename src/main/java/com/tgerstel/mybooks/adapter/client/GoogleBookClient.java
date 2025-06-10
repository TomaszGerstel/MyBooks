package com.tgerstel.mybooks.adapter.client;

import com.tgerstel.mybooks.domain.ExternalBookProvider;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoogleBookClient implements ExternalBookProvider {

    private final RestTemplate restTemplate;
    private final String googleBooksApiUrl;

    public GoogleBookClient(RestTemplate restTemplate, @Value("${google.books.api.url}") String googleBooksApiUrl) {
        this.restTemplate = restTemplate;
        this.googleBooksApiUrl = googleBooksApiUrl;
    }

    @Override
    public PaginatedBooks findBooks(String query, int startIndex, int maxResults) {
        String url = googleBooksApiUrl + "?q=" + query + "&startIndex=" + startIndex + "&maxResults=" + maxResults;

        try {
            GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);
            if (response != null && response.getItems() != null) {
                List<ExternalBook> books = response.getItems().stream().map(this::mapToExternalBook).collect(Collectors.toList());
                boolean hasNextPage = response.getItems().size() == maxResults;
                return new PaginatedBooks(books, response.getTotalItems(), hasNextPage);
            }
            return new PaginatedBooks(List.of(), 0, false);
        } catch (Exception e) {
            log.error("Error fetching books from Google Books API", e);
            return new PaginatedBooks(List.of(), 0, false);
        }
    }

    @Override
    public ExternalBook findBookById(String id) {
        String url = googleBooksApiUrl + "/" + id;
        try {
            GoogleBookItem response = restTemplate.getForObject(url, GoogleBookItem.class);
            return response != null ? mapToExternalBook(response) : null;
        } catch (Exception e) {
            log.error("Error fetching book by ID from Google Books API", e);
            return null;
        }
    }

    private ExternalBook mapToExternalBook(GoogleBookItem item) {
        var authors = item.getVolumeInfo().getAuthors() != null ? item.getVolumeInfo().getAuthors() : List.of("Unknown Author");
        var categories = item.getVolumeInfo().getCategories() != null ? item.getVolumeInfo().getCategories() : List.of("Uncategorized");
        return new ExternalBook(
                item.getId(),
                item.getVolumeInfo().getTitle(),
                String.join(", ", authors),
                item.getVolumeInfo().getPublishedDate(),
                item.getVolumeInfo().getDescription(),
                item.getVolumeInfo().getImageLinks() != null ? item.getVolumeInfo().getImageLinks().getSmallThumbnail() : null,
                String.join(", ", categories)
        );
    }
}