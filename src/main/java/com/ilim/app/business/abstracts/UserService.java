package com.ilim.app.business.abstracts;

import com.ilim.app.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    List<User> getAllUsers();
}
