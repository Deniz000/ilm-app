package com.ilim.app.dto.user;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRolesDTO {
    private String email;
    private String userName;
    private String password;
}
