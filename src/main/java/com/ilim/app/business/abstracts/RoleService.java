package com.ilim.app.business.abstracts;

import com.ilim.app.entities.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role Role);
    Role getRoleById(Long id);
    Role updateRole(Long id, Role RoleDetails);
    void deleteRole(Long id);
    List<Role> getAllRoles();
}
