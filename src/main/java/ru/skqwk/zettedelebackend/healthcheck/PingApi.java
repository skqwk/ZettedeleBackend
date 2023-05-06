package ru.skqwk.zettedelebackend.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Для проверки доступности сервиса
 */
public interface PingApi {
    @GetMapping("/ping")
    String ping();
}
