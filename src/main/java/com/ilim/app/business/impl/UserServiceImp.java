package com.ilim.app.business.impl;

import com.ilim.app.business.services.RoleService;
import com.ilim.app.business.services.UserService;
import com.ilim.app.business.validationhelper.UserValidationHelper;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.user.UserRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapper;
    private final UserValidationHelper validationHelper;

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
    public UserWithRolesDTO updateUser(Long id, UserRequest request) {
        UserEntity user = validationHelper.getUserIfExists(id);
        modelMapper.forRequest().map(request, user);
        return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = validationHelper.getUserIfExists(id);
        userRepository.delete(user);
    }
//    @Override
//    public List<UserWithRolesDTO> getAllUsers() {
//        List<Object[]> results = userRepository.fetchUsersWithRoles();
//        log.error("results: {}", results);
//        List<UserWithRolesDTO> dtos = new ArrayList<>();
//
//        for (Object[] row : results) {
//            Long id = ((Number) row[0]).longValue();
//            String email = (String) row[1];
//            String username = (String) row[2];
//            String password = (String) row[3];
//            String roles = (String) row[4];
//
//            List<String> roleList = Arrays.asList(roles.split(","));
//
//            UserWithRolesDTO dto = new UserWithRolesDTO(id, email, username, password, roleList);
//            dtos.add(dto);
//        }
//
//        return dtos;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userRepository.findByEmail(username)
//                .orElseThrow(()-> new UserNotFoundException("User not found with username: " + username));
//    }
}


