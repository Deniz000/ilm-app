package com.ilim.app.config;

import com.ilim.app.business.impl.UserServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceImp userServiceImp;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, // gelen isteğimiz
                                    @NonNull HttpServletResponse response, // gidecek istepimiz
                                    @NonNull FilterChain filterChain // filtremizi, filtre kuyruğuna ekelyip bunu da kkullan diyoruz
                                    //responsibility design pattern
                                    //doFilter chaindeki bir sonraki filter'ı çağırır
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        //jwt request'te 'Authorization header' içinde geliyor.
        //Authorization: Bearer <token>
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtToken = authorizationHeader.substring(7); // Bearer 7 karakter
        final String userEmail = jwtService.extractUsername(jwtToken);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userServiceImp.loadUserByUsername(userEmail);
            if(jwtService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, //oluşturacağımız sırada null
                        userDetails.getAuthorities()
                );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
