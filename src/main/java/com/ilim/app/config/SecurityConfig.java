package com.ilim.app.config;

import com.ilim.app.config.jwtauth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register").permitAll()// Belirli endpoint'lere izin ver
                        .requestMatchers("/**").permitAll()// Belirli endpoint'lere izin ver
                        .requestMatchers( "/favicon.ico").permitAll()
                        .anyRequest().authenticated() // Geri kalan endpoint'ler için kimlik doğrulama iste
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Her istek için yeni bir oturum oluşturma
                )
                .authenticationProvider(authenticationProvider) // Kimlik doğrulama sağlayıcısını ekle
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT filtreleme mekanizmasını ekle
                .csrf(csrf -> {
            csrf.ignoringRequestMatchers("/api/auth/**");
        });
        return http.build();
    }

}