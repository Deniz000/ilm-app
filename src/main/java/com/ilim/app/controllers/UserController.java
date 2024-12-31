package com.ilim.app.controllers;

import com.ilim.app.business.impl.RoleServiceImpl;
import com.ilim.app.business.impl.UserServiceImp;
import com.ilim.app.business.services.RoleService;
import com.ilim.app.business.services.UserService;
import com.ilim.app.dto.user.UserRequest;
import com.ilim.app.dto.user.UserUpdateRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.Role;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleServiceService;

    @GetMapping("/a/{role}")
    public ResponseEntity<Role> getRole(@PathVariable String role) {
        Role r = roleServiceService.getRoleByName(role);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithRolesDTO> getUserById(@Valid @PathVariable Long id) {
        UserWithRolesDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@Valid @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update")
    public ResponseEntity<UserWithRolesDTO> updateUser(
            @RequestBody UserUpdateRequest request
            ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthorized: Token is missing or invalid.");
        }
       UserWithRolesDTO user = userService.updateUser(request.getId(), request);

        return ResponseEntity.ok(user);
    }
//    @GetMapping
//    public ResponseEntity<List<UserWithRolesDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

}
