package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.model.UserBook;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_books")
@Data
@NoArgsConstructor
public class UserBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    public UserBookEntity(final UserEntity user, final BookEntity book, final  boolean isRead) {
        this.user = user;
        this.book = book;
        this.isRead = isRead;
    }

    public UserBook toDomain() {
        return new UserBook(book.getId(), book.getTitle(), book.getAuthors(), book.getPublicationDate(),
                book.getCoverImageUrl(), isRead);
    }
}