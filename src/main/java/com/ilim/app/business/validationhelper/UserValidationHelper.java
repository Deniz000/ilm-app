package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public record UserValidationHelper(
        UserRepository userRepository
) {
//    public UserEntity validateUser(String username, String password) {}

    public UserEntity getUserIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

}
