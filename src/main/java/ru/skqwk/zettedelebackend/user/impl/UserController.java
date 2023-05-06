package ru.skqwk.zettedelebackend.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.user.UserApi;
import ru.skqwk.zettedelebackend.user.UserService;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountAdminViewDto;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;
import ru.skqwk.zettedelebackend.user.dto.VisibilityRs;
import ru.skqwk.zettedelebackend.user.mapper.UserAccountMapper;
import ru.skqwk.zettedelebackend.util.Formatter;

import java.util.List;

import static ru.skqwk.zettedelebackend.util.Formatter.*;

/**
 * Описание класса
 */
@RestController
@AllArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    @Override
    public UserAccountRs profile(UserAccount userAccount) {
        return UserAccountRs.builder()
                .login(userAccount.getLogin())
                .lastAuth(format(userAccount.getLastAuth()))
                .role(userAccount.getRole().toString())
                .build();
    }

    @Override
    public VisibilityRs visibility(UserAccount userAccount) {
        return VisibilityRs.builder()
                .visibility(userAccount.getVisibility().toString())
                .build();
    }

    @Override
    public void changeVisibility(UserAccount userAccount, Boolean isVisible) {
        userService.changeVisibility(userAccount, isVisible);
    }


    @Override
    public List<UserAccountAdminViewDto> users() {
        List<UserAccount> users = userService.getAllUsers();
        return UserAccountMapper.toAdminViews(users);
    }
}
