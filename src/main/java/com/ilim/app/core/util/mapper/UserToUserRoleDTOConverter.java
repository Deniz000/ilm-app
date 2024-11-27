package com.ilim.app.core.util.mapper;

import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.UserEntity;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserToUserRoleDTOConverter
extends AbstractConverter<UserEntity, UserWithRolesDTO> {
    @Override
    protected UserWithRolesDTO convert(UserEntity source) {
        List<String> roles = source.getRoles().stream().map(role->role.getName().toString()).toList();
        return new UserWithRolesDTO(source.getId(),source.getEmail(),
                source.getUsername(), "", roles);
    }
}