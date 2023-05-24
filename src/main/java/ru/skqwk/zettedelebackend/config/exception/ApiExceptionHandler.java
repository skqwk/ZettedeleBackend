package ru.skqwk.zettedelebackend.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skqwk.zettedelebackend.user.LoginConflictException;

/**
 * Обработчик ошибок
 */
@Slf4j
@Order(0)
@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LoginConflictException.class)
    public ApiError handleLoginConflictException(LoginConflictException ex) {
        log.warn(ex.getMessage(), ex);
        return ApiError.builder()
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ApiError handleAuthException(AuthenticationCredentialsNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return ApiError.builder()
                .message(ex.getMessage())
                .build();
    }
}
