package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.domain.model.UserBook;
import com.tgerstel.mybooks.domain.model.ExternalBook;
import com.tgerstel.mybooks.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
    void delete(User user);
    boolean existsByUsername(String username);

}
