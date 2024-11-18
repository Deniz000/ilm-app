//package com.ilim.app.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/api/users").permitAll() // /api/users endpoint'ini herkese açık yapar
//                        .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulaması gerektirir
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .csrf((csrf) -> csrf.disable()); // CSRF'yi devre dışı bırakmak için
//
//        return http.build();
//    }
//}
