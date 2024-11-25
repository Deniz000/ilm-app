package com.ilim.app.business.impl;

import com.ilim.app.business.services.RoleService;
import com.ilim.app.dataAccess.RoleRepository;
import com.ilim.app.entities.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository RoleRepository;

    public RoleServiceImpl(RoleRepository RoleRepository) {
        this.RoleRepository = RoleRepository;
    }

    @Override
    public Role createRole(Role Role) {
        return RoleRepository.save(Role);
    }

    @Override
    public Role getRoleById(Long id) {
        return RoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
    }

    @Override
    public Role updateRole(Long id, Role RoleDetails) {
        Role Role = getRoleById(id);
        Role.setName(RoleDetails.getName());
        return RoleRepository.save(Role);
    }

    @Override
    public void deleteRole(Long id) {
        RoleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return RoleRepository.findAll();
    }
}
