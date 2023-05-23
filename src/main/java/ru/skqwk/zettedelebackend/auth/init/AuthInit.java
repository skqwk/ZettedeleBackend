package ru.skqwk.zettedelebackend.auth.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.skqwk.zettedelebackend.user.UserRepo;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;
import ru.skqwk.zettedelebackend.user.domain.UserRole;

import static ru.skqwk.zettedelebackend.user.domain.UserAccountVisibility.PRIVATE;

@Slf4j
@Component
@Profile({"h2","postgres"})
@RequiredArgsConstructor
public class AuthInit implements CommandLineRunner {
    private static final String ADMIN = "ADMIN";

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Run AuthInit");
        userRepo.findByLogin(ADMIN)
                .ifPresentOrElse(admin -> adminIsPresent(), this::adminIsEmpty);
    }

    private void adminIsPresent() {
        log.info("{} is present", ADMIN);
    }

    private void adminIsEmpty () {
        log.info("{} is not present", ADMIN);
        userRepo.save(UserAccount.builder()
                .login(ADMIN)
                .role(UserRole.ADMIN)
                .password(passwordEncoder.encode(ADMIN))
                .visibility(PRIVATE)
                .build());
        log.info("save {}", ADMIN);
    }
}
