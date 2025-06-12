package com.tgerstel.mybooks.domain;

import com.tgerstel.mybooks.configuration.exception.UserAlreadyExistsException;
import com.tgerstel.mybooks.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should save user if username is not taken")
    void registerUserTest_1() {
        // given
        String username = "newUser";
        String email = "newUser@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        User result = userService.registerUser(username, email, rawPassword);

        // then
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        verify(userRepository).save(result);
    }

    @Test
    @DisplayName("Should throw exception if username is already taken")
    void registerUserThrowsExceptionIfUsernameIsAlreadyTaken() {
        // given
        String username = "existingUser";
        String email = "existingUser@example.com";
        String rawPassword = "password123";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User(username, "encodedPassword", email)));

        // when & then
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(username, email, rawPassword));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Find user by username should return user if exists")
    void findByUsernameReturnsUserIfExists() {
        // given
        String username = "existingUser";
        User user = new User(username, "encodedPassword", "existingUser@example.com");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = userService.findByUsername(username);

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("Find user by username should return empty if user does not exist")
    void findByUsernameReturnsEmptyIfUserDoesNotExist() {
        // given
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when
        Optional<User> result = userService.findByUsername(username);

        // then
        assertFalse(result.isPresent());
    }

}