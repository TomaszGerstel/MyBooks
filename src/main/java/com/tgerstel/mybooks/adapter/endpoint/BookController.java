package com.tgerstel.mybooks.adapter.endpoint;

import com.tgerstel.mybooks.domain.BookService;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody final ExternalBook book, @AuthenticationPrincipal final UserDetails user) {
        bookService.saveBookLocally(book, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> removeBook(@PathVariable final String bookId, @AuthenticationPrincipal final UserDetails user) {
        bookService.removeBookFromUserLibrary(bookId, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookId}/read")
    public ResponseEntity<?> updateBook(@PathVariable final String bookId, @AuthenticationPrincipal final UserDetails user) {
        bookService.changeBookReadStatus(bookId, user.getUsername());
        return ResponseEntity.ok().build();
    }

}
