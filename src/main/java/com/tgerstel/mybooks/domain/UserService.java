package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.configuration.exception.UserAlreadyExistsException;
import com.tgerstel.mybooks.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Getter
    private PasswordEncoder passwordEncoder;

    public User registerUser(final String username, final String email, final String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + username);
        }
        final var user = new User(username, passwordEncoder.encode(rawPassword), email);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

}