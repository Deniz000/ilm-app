package com.ilim.app.business.services;

import com.ilim.app.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity user);

    UserEntity getUserById(Long id);

    UserEntity updateUser(Long id, UserEntity userDetails);

    void deleteUser(Long id);

    List<UserEntity> getAllUsers();
}
