package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET_KEY = "kiSQVvyZgp3biLXtqOIp7nDS7jXj+yRxyZeb2pLIyO1Xftoolky1vH1YQaFDkreIvNo9PaEvPdC0nOUDkTmG3g==";  // Use a secure secret key
    private final long JWT_EXPIRATION_TIME = 86400000L;  // 24 hours in milliseconds

    // Method to generate the JWT token
    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    // Method to validate the token (for authentication filters)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseJwtToken(String token) {
        // Replace URL-safe characters with base64 standard characters
        String fixedToken = token.replace("-", "+").replace("_", "/");

        // Decode the JWT
        return Jwts.parser()
                .setSigningKey("your-secret-key") // Secret key used to sign the token
                .parseClaimsJws(fixedToken)
                .getBody();
    }

    // Method to extract username from token (for authentication filters)
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
