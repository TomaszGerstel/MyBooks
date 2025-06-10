package com.tgerstel.mybooks.adapter.endpoint;

import com.tgerstel.mybooks.domain.BookService;
import com.tgerstel.mybooks.domain.UserService;
import com.tgerstel.mybooks.domain.model.PaginatedBooks;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BookService bookService;
    private final UserService userService;

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
    public ResponseEntity<?> getUserLibrary(@AuthenticationPrincipal final UserDetails user) {
        return ResponseEntity.ok(userService.getUserLibrary(user.getUsername()));
    }
}
