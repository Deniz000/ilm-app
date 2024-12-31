package com.ilim.app.business.services;

import com.ilim.app.dto.user.UserUpdateRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;

public interface UserService {
    UserWithRolesDTO getUserById(Long id);
    UserWithRolesDTO updateUser(Long id, UserUpdateRequest userDetails);
    void deleteUser(Long id);
 //   List<UserWithRolesDTO> getAllUsers();
}
