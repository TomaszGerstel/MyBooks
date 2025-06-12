package com.tgerstel.mybooks.domain.model;

public record UserBook(
        String id,
        String title,
        String authors,
        String publicationDate,
        String coverImageUrl,
        boolean isRead
) {

}
