package ru.skqwk.zettedelebackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Тело ответа после авторизации
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRs {
    private String authToken;
}
