package ru.skqwk.zettedelebackend.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;

/**
 * Сервис для работы с информацией о пользователях
 */
public interface UserService extends UserDetailsService {
    UserAccount addNewUser(RegisterRq registerRequest);

    <T extends UserDetails> T getUserByLogin(String login);

    void saveUser(UserAccount userAccount);

    List<UserAccount> getAllUsers();

    void changeVisibility(UserAccount userAccount, Boolean isVisible);
}
