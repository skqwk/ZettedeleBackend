package ru.skqwk.zettedelebackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Тело ответа с информацией о пользователе
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountRs {
    String login;
    String lastAuth;
}
