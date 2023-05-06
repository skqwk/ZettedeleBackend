package ru.skqwk.zettedelebackend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Статусы видимости пользовательского аккаунта
 */
@Getter
@AllArgsConstructor
public enum UserAccountVisibility {
    PRIVATE(Boolean.FALSE),
    PUBLIC(Boolean.TRUE);

    private final boolean isVisible;

    public static UserAccountVisibility ofRequirement(boolean isVisible) {
        return isVisible
                ? PUBLIC
                : PRIVATE;
    }
}
