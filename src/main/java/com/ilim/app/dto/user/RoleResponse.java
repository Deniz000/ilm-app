package com.ilim.app.dto.user;

import com.ilim.app.entities.Role;
import lombok.Data;

@Data
public class RoleResponse {
    private Role.RoleName name;
}
