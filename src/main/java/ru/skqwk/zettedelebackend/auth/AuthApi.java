package ru.skqwk.zettedelebackend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skqwk.zettedelebackend.auth.dto.AuthRq;
import ru.skqwk.zettedelebackend.auth.dto.AuthRs;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;

/**
 * API авторизации/регистрации
 */
public interface AuthApi {
    @PostMapping("/auth")
    AuthRs auth(@RequestBody AuthRq authRq);

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRq registerRq);
}
