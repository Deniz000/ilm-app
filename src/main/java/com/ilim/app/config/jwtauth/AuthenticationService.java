package com.ilim.app.config.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilim.app.business.impl.TokenBlacklistService;
import com.ilim.app.core.exceptions.InvalidRoleException;
import com.ilim.app.dataAccess.RoleRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationResponse register(RegisterRequest request) {
        Role roles = getRoles(request.getRole());
        UserEntity user = UserEntity.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roles))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user,user.getId(), user.getName());
        var refreshToken = jwtService.generateRefreshToken(user,user.getId(), user.getName());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
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
        var jwtToken = jwtService.generateToken(user, user.getId(), user.getName());
        var refreshToken = jwtService.generateRefreshToken(user, user.getId(),user.getName());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    private Role getRoles(String roleName){
        Role.RoleName role = Role.RoleName.fromString(roleName);

        return roleRepository.findByName(role)
                .orElseThrow(() -> new InvalidRoleException("Role not found"));
    }
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.validateToken(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user, user.getId(),user.getName());
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                //dönüşüm
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public void logout(String token) {
        tokenBlacklistService.addTokenToBlacklist(token);

    }
}
