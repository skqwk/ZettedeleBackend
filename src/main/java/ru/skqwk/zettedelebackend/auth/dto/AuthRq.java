package ru.skqwk.zettedelebackend.auth.dto;

/**
 * Тело запроса для авторизации
 */
public record AuthRq(String login, String password) {
}
