package com.tgerstel.mybooks.domain.model;

import java.time.LocalDate;

public record Book(
        String id,
        String title,
        String authors,
        LocalDate publicationDate,
        String coverImageUrl
) {

}
