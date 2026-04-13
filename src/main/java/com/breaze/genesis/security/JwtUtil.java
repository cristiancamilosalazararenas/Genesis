package com.breaze.genesis.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
/**
 * Utility class to work with JWT tokens.
 *
 * This class is used to:
 * - Generate JWT tokens
 * - Read information from tokens
 * - Validate tokens
 */
@Component
public class JwtUtil {

    /**
     * Secret key used to sign the token.
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * Time in milliseconds for token expiration.
     */
    @Value("${jwt.expiration}")
    private long expiration;
    /**
     * Creates the signing key from the secret.
     *
     * @return SecretKey used to sign JWT
     */
    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    /**
     * Generates a JWT token for a user.
     *
     * @param userDetails user information
     * @return JWT token as String
     */
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey())
                .compact();
    }
    /**
     * Gets the username (email) from the token.
     *
     * @param token JWT token
     * @return username inside token
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }
    /**
     * Validates the token with user details.
     *
     * @param token JWT token
     * @param userDetails user information
     * @return true if token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
    /**
     * Checks if token is expired.
     *
     * @param token JWT token
     * @return true if expired
     */
    private boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }
    /**
     * Reads the claims from the token.
     *
     * @param token JWT token
     * @return Claims object
     */

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token");
        }
    }

    /**
     * Validates token without user details.
     *
     * @param token JWT token
     * @return true if token is not expired
     */
    public boolean isTokenValid(String token) {
        return !isExpired(token);
    }
}
