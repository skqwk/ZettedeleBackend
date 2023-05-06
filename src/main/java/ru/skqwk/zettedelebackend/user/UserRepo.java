package ru.skqwk.zettedelebackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByLogin(String login);
}
