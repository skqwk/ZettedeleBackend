package ru.skqwk.zettedelebackend.user.mapper;

import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.dto.UserAccountAdminViewDto;

import java.util.List;
import java.util.stream.Collectors;

import static ru.skqwk.zettedelebackend.util.Formatter.format;

/**
 * Маппер для пользовательских данных
 */
public class UserAccountMapper {
    public static UserAccountAdminViewDto toAdminView(UserAccount userAccount) {
        return UserAccountAdminViewDto.builder()
                .visibility(userAccount.getVisibility().toString())
                .login(userAccount.getLogin())
                .lastAuth(format(userAccount.getLastAuth()))
                .role(userAccount.getRole().toString())
                .build();
    }

    public static List<UserAccountAdminViewDto> toAdminViews(List<UserAccount> userAccounts) {
        return userAccounts.stream()
                .map(UserAccountMapper::toAdminView)
                .collect(Collectors.toList());
    }
}
