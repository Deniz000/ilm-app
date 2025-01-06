package com.ilim.app.controllers;

import com.ilim.app.business.services.RoleService;
import com.ilim.app.business.services.UserService;
import com.ilim.app.dto.user.UserUpdateRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

       UserWithRolesDTO user = userService.updateUser(request.getId(), request);
        return ResponseEntity.ok(user);
    }
//    @GetMapping
//    public ResponseEntity<List<UserWithRolesDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

}
