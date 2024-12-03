package com.ilim.app.business.validationhelper;

import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserEntity> {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean validateById(Long id)  {
        return userRepository.existsById(id);
    }

    @Override
    public boolean validateByName(String name){return false;}

    @Override
    public UserEntity getIfExistsById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + " not found"));
    }

    @Override
    public UserEntity getIfExistsByName(String name) {
        return null;
    }
}
