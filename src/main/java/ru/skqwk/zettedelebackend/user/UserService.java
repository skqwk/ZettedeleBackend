package ru.skqwk.zettedelebackend.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;

/**
 * Сервис для работы с информацией о пользователях
 */
public interface UserService extends UserDetailsService {
    void addNewUser(RegisterRq registerRequest);

    <T extends UserDetails> T getUserByLogin(String login);

    void saveUser(UserAccount userAccount);
}
