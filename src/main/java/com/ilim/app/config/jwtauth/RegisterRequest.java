package com.ilim.app.config.jwtauth;

import com.ilim.app.business.validation.PasswordMatches;
import com.ilim.app.business.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@PasswordMatches
public class RegisterRequest {
    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 16)
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String role;
}
