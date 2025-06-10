package com.tgerstel.mybooks.domain.model;

public record Book(
        String id,
        String title,
        String authors,
        String publicationDate,
        String coverImageUrl
) {

}
