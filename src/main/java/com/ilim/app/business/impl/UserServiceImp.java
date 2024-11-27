package com.ilim.app.business.impl;

import com.ilim.app.business.services.RoleService;
import com.ilim.app.business.services.UserService;
import com.ilim.app.business.validationhelper.UserValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.core.util.mapper.PersistentSetToSetConverter;
import com.ilim.app.core.util.mapper.UserToUserRoleDTOConverter;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.user.UserRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperService modelMapper;
    private final UserValidationHelper validationHelper;
    private final RoleService roleService;
    private TypeMap<UserEntity, UserWithRolesDTO> propertyMapper;

    @Transactional
    public UserWithRolesDTO createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleService.getRoleByName(takeRoleName(request.getRoleName()));
        user.getRoles().add(role);
        modelMapper.forRequest().map(request, user);
        userRepository.save(user);
        return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
    }

    private String takeRoleName(String roleName) {
        return switch (roleName.toUpperCase()) {
            case "ADMIN" -> "ADMIN";
            case "TEACHER" -> "TEACHER";
            case "STUDENT" -> "STUDENT";
            default -> "not found";
        };
    }

    public UserWithRolesDTO getUserById(Long id) {
        UserEntity user = validationHelper.getUserIfExists(id);
        return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
    }

    @Override
    public UserWithRolesDTO updateUser(Long id, UserRequest userDetails) {
        UserEntity user = validationHelper.getUserIfExists(id);
        modelMapper.forRequest().map(userDetails, user);
        return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = validationHelper.getUserIfExists(id);
        userRepository.delete(user);
    }
    @Override
    public List<UserWithRolesDTO> getAllUsers() {
        List<Object[]> results = userRepository.fetchUsersWithRoles();

        List<UserWithRolesDTO> dtos = new ArrayList<>();

        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String email = (String) row[1];
            String username = (String) row[2];
            String password = (String) row[3];
            String roles = (String) row[4];

            List<String> roleList = Arrays.asList(roles.split(","));

            UserWithRolesDTO dto = new UserWithRolesDTO(id, email, username, password, roleList);
            dtos.add(dto);
        }

        return dtos;
    }}


