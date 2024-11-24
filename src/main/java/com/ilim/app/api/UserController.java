package com.ilim.app.api;

import com.ilim.app.business.abstracts.UserService;
import com.ilim.app.entities.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Kullanıcı oluşturma
    @PostMapping
    public ResponseEntity<UserEntity> createUserEntity(@RequestBody UserEntity UserEntity) {
        UserEntity createdUserEntity = userService.createUser(UserEntity);
        return new ResponseEntity<>(createdUserEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserEntityById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);
        return ResponseEntity.ok(userEntity); // json veriyle birlikte döndürmek
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUserEntitys() {
        List<UserEntity> UserEntity = userService.getAllUsers();
        return ResponseEntity.ok(UserEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUserEntity(@PathVariable Long id, @RequestBody UserEntity UserEntityDetails) {
        UserEntity updatedUserEntity = userService.updateUser(id, UserEntityDetails);
        return ResponseEntity.ok(updatedUserEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable Long id) {
        if (id == null) return ResponseEntity.badRequest().build();
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

