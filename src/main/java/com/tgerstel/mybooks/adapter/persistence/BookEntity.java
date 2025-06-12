package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.model.ExternalBook;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    private String id;
    private String title;
    private String authors;
    private String publicationDate;
    private String coverImageUrl;

    public BookEntity (ExternalBook book) {
        this.id = book.id();
        this.title = book.title();
        this.authors = book.authors();
        this.publicationDate = book.publicationDate();
        this.coverImageUrl = book.coverImageUrl();
    }

}
