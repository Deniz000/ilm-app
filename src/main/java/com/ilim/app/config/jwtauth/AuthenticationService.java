package com.ilim.app.config.jwtauth;

import com.ilim.app.core.exceptions.InvalidRoleException;
import com.ilim.app.dataAccess.RoleRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role roles = getRoles(request.getRole());
        UserEntity user = UserEntity.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roles))
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toList()))
                .build();
    }


    private Role getRoles(String roleName){
        Role.RoleName role = Role.RoleName.fromString(roleName);

        return roleRepository.findByName(role)
                .orElseThrow(() -> new InvalidRoleException("Role not found"));
    }

}
