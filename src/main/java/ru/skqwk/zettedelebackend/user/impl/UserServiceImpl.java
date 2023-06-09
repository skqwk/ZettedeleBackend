package ru.skqwk.zettedelebackend.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.auth.dto.RegisterRq;
import ru.skqwk.zettedelebackend.user.LoginConflictException;
import ru.skqwk.zettedelebackend.user.UserRepo;
import ru.skqwk.zettedelebackend.user.UserService;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.domain.UserAccountVisibility;
import ru.skqwk.zettedelebackend.user.domain.UserRole;

import java.util.List;

import static ru.skqwk.zettedelebackend.user.domain.UserAccountVisibility.*;

/**
 * Сервис с реализацией методов по работе с пользователями
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepo
                .findByLogin(login)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Регистрация пользователя.
     *
     * @param registerRequest Данные регистрирующегося пользователя.
     * @throws LoginConflictException Если пользователь с таким login уже существует.
     */
    @Override
    public UserAccount addNewUser(RegisterRq registerRequest) {
        UserAccount userAccount =
                UserAccount.builder()
                        .role(UserRole.USER)
                        .visibility(PUBLIC)
                        .login(registerRequest.login())
                        .password(passwordEncoder.encode(registerRequest.password()))
                        .build();
        UserAccount savedUser;
        try {
            savedUser = userRepo.save(userAccount);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Попытка регистрации с существующим логином = {}", registerRequest.login());
            throw new LoginConflictException(
                    String.format("Логин '%s' уже используется", registerRequest.login()));
        }
        log.info("Пользователь с логином = {} успешно зарегистрировался", registerRequest.login());
        return savedUser;
    }

    @Override
    public UserDetails getUserByLogin(String login) {
        return userRepo.findByLogin(login)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void saveUser(UserAccount userAccount) {
        userRepo.save(userAccount);
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void changeVisibility(UserAccount userAccount, Boolean isVisible) {
        UserAccountVisibility visibility = UserAccountVisibility.ofRequirement(isVisible);
        userAccount.setVisibility(visibility);
        userRepo.save(userAccount);
    }
}
