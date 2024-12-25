package com.ilim.app.config.jwtauth;

import com.ilim.app.business.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationRequest {
    @ValidEmail
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    private String password;
}
