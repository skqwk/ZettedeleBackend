package ru.skqwk.zettedelebackend.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;

/**
 * API для работы с данными пользователя
 */
public interface UserApi {
    @GetMapping("/profile")
    UserAccountRs profile(@AuthenticationPrincipal UserAccount userAccount);
}
