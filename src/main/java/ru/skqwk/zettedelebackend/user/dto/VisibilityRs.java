package ru.skqwk.zettedelebackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Тело ответа с информацией о видимости
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisibilityRs {
    private String visibility;
}
