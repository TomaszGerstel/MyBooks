package com.tgerstel.mybooks.adapter.endpoint;

import com.tgerstel.mybooks.domain.BookService;
import com.tgerstel.mybooks.domain.model.UserBook;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedBooks> searchBooks(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        int startIndex = page * size;
        PaginatedBooks paginatedBooks = bookService.searchBooks(q, startIndex, size);
        return ResponseEntity.ok(paginatedBooks);
    }

    @GetMapping("/user-library")
    public ResponseEntity<List<UserBook>> getUserLibrary(@AuthenticationPrincipal final UserDetails user) {
        return ResponseEntity.ok(bookService.getUserLibrary(user.getUsername()));
    }
}
