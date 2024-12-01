package com.ilim.app.business.services;

import com.ilim.app.dto.user.UserRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserWithRolesDTO getUserById(Long id);
    UserWithRolesDTO updateUser(Long id, UserRequest userDetails);
    void deleteUser(Long id);
 //   List<UserWithRolesDTO> getAllUsers();
}
