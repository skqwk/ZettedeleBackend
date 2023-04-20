package ru.skqwk.zettedelebackend.auth.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.auth.AuthService;
import ru.skqwk.zettedelebackend.config.security.jwt.JwtTokenUtil;
import ru.skqwk.zettedelebackend.user.UserAccount;
import ru.skqwk.zettedelebackend.user.UserService;

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
    public String authenticate(String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        try {
            authManager.authenticate(token);
        } catch (BadCredentialsException ex) {
            log.warn("User with login = {} can't auth", login);
        }
        UserAccount userAccount = userService.getUserByLogin(login);
        userAccount.setLastAuth(Instant.now());
        userService.saveUser(userAccount);
        return jwtTokenUtil.generateToken(userAccount);
    }
}
