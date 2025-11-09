package jp.kernelpanic.auth.core.jwt;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final String secretKeyPath;

    private final SecretKey signingKey;

    private final long EXPIRATION_TIME = 3600000;

    public JwtTokenProvider(ResourceLoader resourceLoader,
        @Value("${jwt.secret.resource}") String secretKeyPath) {

        this.secretKeyPath = secretKeyPath;
        try {
            System.out.println("HAL Configuration: Secret key path=" + secretKeyPath);

            Resource resource = resourceLoader.getResource(secretKeyPath);
            if (!resource.exists()) {
                throw new RuntimeException("HAL Core Mission: Secret key resource not found. Check path and build: " + secretKeyPath);
            }

            byte[] keyBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());

            if (keyBytes.length < 64) {
                 throw new IllegalArgumentException("HAL Core Mission: Insufficient secret key byte length (Current: " + keyBytes.length + " bytes). HS512 requires >= 64 bytes.");
            }

            this.signingKey = Keys.hmacShaKeyFor(keyBytes);

        } catch (Exception e) {
            throw new RuntimeException("HAL Core Mission: Failed to initialize JWT Provider. Details: " + e.getMessage(), e);
        }
    }

    private SecretKey getSigningKey() {
        return this.signingKey;
    }

    /**
     * Authenticates a user and generates a JWT.
     */
    public String generateToken(String loginId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validates the JWT token (Signature and Expiration).
     * @param token JWT string
     * @return true if valid
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.err.println("JWT validation failed: " + e.getMessage());
            return false;
        }
    }


    /**
     * Extracts the Subject (User ID) from the token claims.
     * @param token JWT string
     * @return User ID
     */
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}

