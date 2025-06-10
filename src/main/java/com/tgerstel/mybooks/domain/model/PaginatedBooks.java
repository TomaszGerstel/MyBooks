package com.tgerstel.mybooks.domain.model;

import java.util.List;

public record PaginatedBooks(
        List<ExternalBook> books,
        int totalItems,
        boolean hasNextPage
) {
}