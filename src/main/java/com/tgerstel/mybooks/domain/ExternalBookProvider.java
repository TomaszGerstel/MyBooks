package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;

public interface ExternalBookProvider {
    PaginatedBooks findBooks(String query, int startIndex, int maxResults);
    ExternalBook findBookById(String id);
}
