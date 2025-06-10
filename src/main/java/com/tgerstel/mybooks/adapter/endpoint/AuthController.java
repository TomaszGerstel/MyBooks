package com.tgerstel.mybooks.adapter.endpoint;

import com.tgerstel.mybooks.configuration.JwtUtils;
import com.tgerstel.mybooks.domain.UserService;
import com.tgerstel.mybooks.domain.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid final RegistrationRequest request, final Errors error) throws Exception {
        userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.findByUsername(request.getUsername());
        if (user.isPresent() && userService.getPasswordEncoder().matches(request.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateJwtToken(request.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(403).body("Invalid credentials");
    }

}