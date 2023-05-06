package ru.skqwk.zettedelebackend.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, String> {
    List<Event> findAllByUser(UserAccount userAccount);
}
