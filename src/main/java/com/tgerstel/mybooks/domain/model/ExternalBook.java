package com.tgerstel.mybooks.domain.model;

public record ExternalBook(
        String id,
        String title,
        String authors,
        String publicationDate,
        String description,
        String coverImageUrl,
        String category
) {

}
