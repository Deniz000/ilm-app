package com.ilim.app.business.impl;

import com.ilim.app.business.services.RoleService;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.RoleRepository;
import com.ilim.app.entities.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public Role getRoleByName(String roleName) {
      return roleRepository.findByName(Role.RoleName.valueOf(roleName))
              .orElseThrow(() -> new EntityNotFoundException(String.format("Role with name %s not found", roleName)));
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
    }
}
