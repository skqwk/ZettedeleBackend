package ru.skqwk.zettedelebackend.event;

import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с событиями
 */
public interface EventService {
    List<Event> loadMissingEvents(UserAccount userAccount, Map<UUID, HybridTimestamp> diffVectorVersion);

    Event saveEvent(UserAccount userAccount, Event event);

    List<Event> getAllEvents(UserAccount userAccount);

    void saveAllEvents(UserAccount userAccount, List<Event> events);
}
