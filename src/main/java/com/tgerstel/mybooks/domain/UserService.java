package com.tgerstel.mybooks.domain;

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

    public User registerUser(String username, String email, String rawPassword) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("User already exists with username: " + username);
        }
        User user = new User(username, passwordEncoder.encode(rawPassword), email);
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}