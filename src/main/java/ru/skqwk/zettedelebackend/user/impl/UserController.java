package ru.skqwk.zettedelebackend.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.user.UserApi;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountRs;

/**
 * Описание класса
 */
@RestController
@AllArgsConstructor
public class UserController implements UserApi {
    @Override
    public UserAccountRs profile(UserAccount userAccount) {
        return UserAccountRs.builder()
                .login(userAccount.getLogin())
                .lastAuth(userAccount.getLastAuth().toString())
                .build();
    }
}
