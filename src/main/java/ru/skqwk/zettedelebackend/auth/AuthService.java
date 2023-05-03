package ru.skqwk.zettedelebackend.auth;

import ru.skqwk.zettedelebackend.auth.dto.AuthRs;

/**
 * Сервис для методов авторизации
 */
public interface AuthService {
    AuthRs authenticate(String login, String password);
}
