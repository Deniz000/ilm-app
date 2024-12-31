package com.ilim.app.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long id;
    private String username;
    private String email;
    private String password;
}
