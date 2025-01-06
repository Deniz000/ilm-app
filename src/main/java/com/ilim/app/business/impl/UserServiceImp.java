package com.ilim.app.business.impl;

import com.ilim.app.business.services.UserService;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.config.jwtauth.JwtService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.dto.user.UserUpdateRequest;
import com.ilim.app.dto.user.UserWithRolesDTO;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.ilim.app.core.util.EntityUpdateUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapper;
    private final ValidationHelper validationHelper;
    private final JwtService jwtService;

    public UserWithRolesDTO getUserById(Long id) {
        UserEntity user = validationHelper.getIfExistsById(UserEntity.class, id);
        return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
    }

    @Override
    public UserWithRolesDTO updateUser(Long id, UserUpdateRequest request) {
        try {
            UserEntity user = validationHelper.getIfExistsById(UserEntity.class, id);
            boolean isThere = validationHelper.getUserValidator().validateByEmail(request.getEmail());
            if (isThere)
                throw new EntityAlreadyExits("Change The Email");

            updateIfNotNull(user::setUsername, request.getUserName());
            updateIfNotNull(user::setEmail, request.getEmail());
            updateIfNotNull(user::setPassword, request.getPassword());
            userRepository.save(user);

            return modelMapper.forResponse().map(user, UserWithRolesDTO.class);
        } catch (EntityAlreadyExits ignored) {

        }

        throw new RuntimeException("Unexpected error occurred.");
    }


    @Override
    public void deleteUser(Long id) {
        UserEntity user = validationHelper.getIfExistsById(UserEntity.class, id);
        userRepository.delete(user);
    }


//    @Override
//    public List<UserWithRolesDTO> getAllUsers() {
//        List<Object[]> results = userRepository.fetchUsersWithRoles();
//        log.error("results: {}", results);
//        List<UserWithRolesDTO> dtos = new ArrayList<>();
//
//        for (Object[] row : results) {
//            Long id = ((Number) row[0]).longValue();
//            String email = (String) row[1];
//            String username = (String) row[2];
//            String password = (String) row[3];
//            String roles = (String) row[4];
//
//            List<String> roleList = Arrays.asList(roles.split(","));
//
//            UserWithRolesDTO dto = new UserWithRolesDTO(id, email, username, password, roleList);
//            dtos.add(dto);
//        }
//
//        return dtos;
//    }
//
}


