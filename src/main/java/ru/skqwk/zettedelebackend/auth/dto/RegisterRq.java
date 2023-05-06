package ru.skqwk.zettedelebackend.auth.dto;

/**
 * Тело запроса регистрации
 */
public record RegisterRq(String login, String password) {
}
