package ru.skqwk.zettedelebackend.auth;

/**
 * Сервис для методов авторизации
 */
public interface AuthService {
    String authenticate(String login, String password);
}
