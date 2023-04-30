package ru.skqwk.zettedelebackend.auth.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.auth.AuthApi;
import ru.skqwk.zettedelebackend.auth.AuthService;
import ru.skqwk.zettedelebackend.auth.dto.AuthRq;
import ru.skqwk.zettedelebackend.auth.dto.AuthRs;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;
import ru.skqwk.zettedelebackend.sync.VectorVersionService;
import ru.skqwk.zettedelebackend.user.UserService;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

/**
 * Реализация контроллера для регистрации/авторизации
 */
@RestController
@AllArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final UserService userService;
    private final VectorVersionService vectorVersionService;

    @Override
    public AuthRs auth(AuthRq authRq) {
        String authToken = authService.authenticate(authRq.login(), authRq.password());
        return AuthRs.builder()
                .authToken(authToken)
                .build();
    }

    @Override
    public ResponseEntity<?> register(RegisterRq registerRq) {
        UserAccount userAccount = userService.addNewUser(registerRq);
        vectorVersionService.createNewVector(userAccount);
        return ResponseEntity.ok().build();
    }
}
