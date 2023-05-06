package ru.skqwk.zettedelebackend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skqwk.zettedelebackend.auth.dto.AuthRq;
import ru.skqwk.zettedelebackend.auth.dto.AuthRs;
import ru.skqwk.zettedelebackend.auth.dto.NodeRs;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;

/**
 * API авторизации/регистрации
 */
public interface AuthApi {
    @PostMapping("/auth")
    AuthRs auth(@RequestBody AuthRq authRq);

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRq registerRq);

    @GetMapping("/node")
    NodeRs node(@AuthenticationPrincipal UserAccount userAccount);
}
