package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User findById(Long id);
    User save(User user);
    void delete(User user);
    boolean existsByUsername(String username);
    List<Book> findBooksByUserId(Long userId);
}
