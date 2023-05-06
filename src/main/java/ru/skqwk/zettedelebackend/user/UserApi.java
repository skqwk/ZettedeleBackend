package ru.skqwk.zettedelebackend.user;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountAdminViewDto;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;
import ru.skqwk.zettedelebackend.user.dto.VisibilityRs;

import java.util.List;

/**
 * API для работы с данными пользователя
 */
public interface UserApi {
    @GetMapping("/profile")
    UserAccountRs profile(@AuthenticationPrincipal UserAccount userAccount);

    @GetMapping("/visibility")
    VisibilityRs visibility(@AuthenticationPrincipal UserAccount userAccount);

    @PostMapping("/changeVisibility")
    void changeVisibility(@AuthenticationPrincipal UserAccount userAccount,
                          @RequestParam("isVisible") Boolean isVisible);

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/users")
    List<UserAccountAdminViewDto> users();
}
