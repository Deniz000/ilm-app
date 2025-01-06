package com.ilim.app.config.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.expiration:360000}") // Varsayılan 24 saat
    private long jwtExpirationMs;

    @Value("${jwt.refresh-token.expiration:604800000}") // Varsayılan 7 gün
    private long jwtRefreshExpirationMs;

//    private long jwtExpirationMs;
    @Value("${jwt.secret}") // will find better solution
    private String secretKey;

    public String extractEmail(String token) {
        log.error("extractEmail");
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.error("extractClaim");
        final Claims claim = extractClaims(token);
        return claimsResolver.apply(claim);
    }

    // extract işlemi için bir signing key ' e ihtiyacımız var'mış
    private Claims extractClaims(String token) {
        log.error("extractClaims");
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder()
                .decode(secretKey);
        return new SecretKeySpec(bytes, "HmacSHA256"); }

//    private SecretKey getSignInKey() {
//        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
//                SignatureAlgorithm.HS256.getJcaName());
//    }

    public String generateToken(
            UserDetails userDetails,
                                Long userId,
            String name

                                ) {
        return buildToken(userDetails, userId,name, jwtExpirationMs);
    }


    public String generateRefreshToken(
            UserDetails userDetails,
            Long id,
            String name
    ) {
        return buildToken(userDetails, id, name, jwtRefreshExpirationMs);
    }
    private String buildToken(UserDetails userDetails,
                              Long userId,
                              String name,
                              Long expiration) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", userId)
                .claim("name", name)
                .claim("role", userDetails.getAuthorities()) // Role bilgisini ekliyoruz
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasRole(String token, String roleName) {
        Claims claims = extractClaims(token);
        List<?> roles = (List<?>) claims.get("role");
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        return roles.contains(roleName);
    }

}