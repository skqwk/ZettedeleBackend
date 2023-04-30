package ru.skqwk.zettedelebackend.sync;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skqwk.zettedelebackend.sync.domain.VectorVersion;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Optional;
import java.util.UUID;


public interface VectorVersionRepo extends JpaRepository<VectorVersion, UUID> {
    Optional<VectorVersion> findByUser(UserAccount user);
}
