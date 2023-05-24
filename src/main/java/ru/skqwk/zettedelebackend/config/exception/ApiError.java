package ru.skqwk.zettedelebackend.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Описание класса
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
}
