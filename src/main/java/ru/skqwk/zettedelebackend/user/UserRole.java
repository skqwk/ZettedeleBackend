package ru.skqwk.zettedelebackend.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Роли пользователей в Zettedele:
 * - Обычный пользователь
 * - Администратор
 **/
public enum UserRole {
    USER,
    MANAGER;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", this.name())));
    }
}
