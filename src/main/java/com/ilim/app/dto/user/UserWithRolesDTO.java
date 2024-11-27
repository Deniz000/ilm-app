package com.ilim.app.dto.user;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRolesDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<String> roles; // Role isimlerini döndürmek için
}
