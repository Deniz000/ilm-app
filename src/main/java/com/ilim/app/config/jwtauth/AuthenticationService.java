package com.ilim.app.config.jwtauth;

import com.ilim.app.business.impl.TokenBlacklistService;
import com.ilim.app.core.exceptions.InvalidRoleException;
import com.ilim.app.core.exceptions.UserNotFoundException;
import com.ilim.app.dataAccess.RoleRepository;
import com.ilim.app.dataAccess.UserRepository;
import com.ilim.app.entities.Role;
import com.ilim.app.entities.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        String jwtToken = jwtService.generateToken(user, user.getId());
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
        var jwtToken = jwtService.generateToken(user, user.getId());
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

    public String refreshToken(String token) {
        try {
            log.error("Step 1: Token işleniyor -> {}", token);
            String email = jwtService.extractEmail(token); // Süresi dolmamış tokenlar burada işlenir
            log.error("Step 2: Token'dan çıkarılan email -> {}", email);

            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + email));
            log.error("Step 3: Kullanıcı bulundu -> {}", user);

            if (jwtService.validateToken(token, user)) {
                log.error("Step 4: Token doğrulandı.");
                return token;
            }

            log.error("Step 5: Yeni token oluşturuluyor...");
            return jwtService.generateToken(user, user.getId());
        } catch (ExpiredJwtException ex) {
            log.error("Step 6: Süresi dolmuş token -> {}", ex.getMessage());
            String email = ex.getClaims().getSubject(); // "sub" alanını çıkar
            log.error("Step 7: Token'dan çıkarılan email -> {}", email);

            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + email));
            log.error("Step 8: Kullanıcı bulundu -> {}", user);

            log.error("Step 9: Yeni token oluşturuluyor...");
            return jwtService.generateToken(user, user.getId());
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("Step 10: Geçersiz veya bozuk token -> {}", e.getMessage());
            throw new IllegalArgumentException("Geçersiz refresh token", e);
        } catch (Exception e) {
            log.error("Step 11: Beklenmeyen hata -> {}", e.getMessage());
            throw new IllegalArgumentException("Beklenmeyen hata oluştu.", e);
        }
    }



    public void logout(String token) {
        tokenBlacklistService.addTokenToBlacklist(token);

    }
}
