package com.ilim.app.business.services;

import com.ilim.app.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);
    Role getRoleById(Long id);
}
