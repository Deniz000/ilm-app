package com.ilim.app.dto.user;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String roleName;
    private String email;
    private String password;
}
