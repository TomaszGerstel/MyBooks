package com.tgerstel.mybooks.adapter.endpoint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Enter the name")
    private String username;
    @Email(message = "Enter a valid email")
    @NotBlank(message = "Enter your email")
    private String email;
    @NotBlank(message = "Enter password")
    private String password;
}
