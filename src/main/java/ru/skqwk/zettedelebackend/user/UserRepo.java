package ru.skqwk.zettedelebackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByLogin(String login);
}
