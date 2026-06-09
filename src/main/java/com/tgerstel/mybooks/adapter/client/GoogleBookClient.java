package com.tgerstel.mybooks.adapter.client;

import com.tgerstel.mybooks.domain.ExternalBookProvider;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoogleBookClient implements ExternalBookProvider {

    private final RestTemplate restTemplate;
    private final String googleBooksApiUrl;
    private final String googleBooksApiKey;

    public GoogleBookClient(
            final RestTemplate restTemplate,
            @Value("${google.books.api.url}") final String googleBooksApiUrl,
            @Value("${google.books.api.key}") final String googleBooksApiKey) {
        this.restTemplate = restTemplate;
        this.googleBooksApiUrl = googleBooksApiUrl;
        this.googleBooksApiKey = googleBooksApiKey;

    }

    @Override
    public PaginatedBooks findBooks(final String query, final int startIndex, final int maxResults) {

        final String url = UriComponentsBuilder
                .fromUriString(googleBooksApiUrl)
                .queryParam("q", query)
                .queryParam("startIndex", startIndex)
                .queryParam("maxResults", maxResults)
                .queryParam("key", googleBooksApiKey)
                .build()
                .toUriString();

        try {
            final var response = restTemplate.getForObject(url, GoogleBooksResponse.class);
            if (response != null && response.items() != null) {
                final List<ExternalBook> books = response.items().stream().map(this::mapToExternalBook).collect(Collectors.toList());
                final boolean hasNextPage = response.items().size() == maxResults;
                return new PaginatedBooks(books, response.totalItems(), hasNextPage);
            }
            return new PaginatedBooks(List.of(), 0, false);
        } catch (Exception e) {
            log.error("Error fetching books from Google Books API", e);
            return new PaginatedBooks(List.of(), 0, false);
        }
    }

    @Override
    public ExternalBook findBookById(final String id) {
        final String url = googleBooksApiUrl + "/" + id;
        try {
            final var response = restTemplate.getForObject(url, GoogleBookItem.class);
            return response != null ? mapToExternalBook(response) : null;
        } catch (Exception e) {
            log.error("Error fetching book by ID from Google Books API", e);
            return null;
        }
    }

    private ExternalBook mapToExternalBook(final GoogleBookItem item) {
        final var authors = item.getVolumeInfo().getAuthors() != null ? item.getVolumeInfo().getAuthors() : List.of("Unknown Author");
        final var categories = item.getVolumeInfo().getCategories() != null ? item.getVolumeInfo().getCategories() : List.of("Uncategorized");
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