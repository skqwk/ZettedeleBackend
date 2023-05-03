package ru.skqwk.zettedelebackend.user;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountAdminViewDto;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;

import java.util.List;

/**
 * API для работы с данными пользователя
 */
public interface UserApi {
    @GetMapping("/profile")
    UserAccountRs profile(@AuthenticationPrincipal UserAccount userAccount);

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/users")
    List<UserAccountAdminViewDto> users();
}
