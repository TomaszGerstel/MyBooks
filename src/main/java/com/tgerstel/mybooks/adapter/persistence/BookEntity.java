package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.model.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    private String id;
    private String title;
    private String authors;
    private String isbn;
    private LocalDate publicationDate;
    private String coverImageUrl;

    public Book toDomain() {
        return new Book(id, title, authors, publicationDate, coverImageUrl);
    }

}
