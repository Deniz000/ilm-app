package com.ilim.app.controllers;

import com.ilim.app.business.impl.RoleServiceImpl;
import com.ilim.app.business.impl.UserServiceImp;
import com.ilim.app.dto.user.UserRequest;
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
    private final UserServiceImp userService;
    private final RoleServiceImpl roleServiceService;
    private final UserServiceImp userServiceImp;

    @GetMapping("/a/{r}")
    public ResponseEntity<Role> getRole(@PathVariable String r) {
        Role role = roleServiceService.getRoleByName(r);
        return ResponseEntity.ok(role);
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
    @PutMapping("/{id}")
    public ResponseEntity<UserWithRolesDTO> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserRequest request) {
        UserWithRolesDTO user = userServiceImp.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
//    @GetMapping
//    public ResponseEntity<List<UserWithRolesDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

}
