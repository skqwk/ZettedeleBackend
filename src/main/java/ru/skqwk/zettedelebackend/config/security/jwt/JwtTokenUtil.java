package ru.skqwk.zettedelebackend.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Методы для генерации JWT-токена
 */
@Component
@AllArgsConstructor
public class JwtTokenUtil {
    private static final String AUTHORITIES = "authorities";

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    public <T extends UserDetails> String generateToken(T userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES, userDetails.getAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(getExpiration())
                .signWith(secretKey)
                .compact();
    }

    private Date getExpiration() {
        Instant expiration = Instant.now().plus(jwtConfig.getTokenExpirationAfterDays(), DAYS);
        return Date.from(expiration);
    }

    public String getUsernameFromToken(String token) {
        Jws<Claims> claimsJws =
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body.getSubject();
    }
}
