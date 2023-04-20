package ru.skqwk.zettedelebackend.user;

/**
 * Исключение при попытке регистрации с уже существующим логином
 */
public class LoginConflictException extends RuntimeException {
    public LoginConflictException(String message) {
        super(message);
    }
}
