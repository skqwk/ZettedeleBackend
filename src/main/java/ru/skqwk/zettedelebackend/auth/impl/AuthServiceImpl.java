package ru.skqwk.zettedelebackend.auth.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.auth.AuthService;
import ru.skqwk.zettedelebackend.auth.dto.AuthRs;
import ru.skqwk.zettedelebackend.config.security.jwt.JwtTokenUtil;
import ru.skqwk.zettedelebackend.user.UserService;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.time.Instant;

/**
 * Реализация методов авторизации
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthRs authenticate(String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        try {
            authManager.authenticate(token);
        } catch (BadCredentialsException ex) {
            log.warn("User with login = {} can't auth", login);
        }
        UserAccount userAccount = userService.getUserByLogin(login);
        userAccount.setLastAuth(Instant.now());
        userService.saveUser(userAccount);
        return AuthRs.builder()
                .role(userAccount.getRole().toString())
                .authToken(jwtTokenUtil.generateToken(userAccount))
                .build();
    }
}
