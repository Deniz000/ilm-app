package com.ilim.app.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @NotNull(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;
}
