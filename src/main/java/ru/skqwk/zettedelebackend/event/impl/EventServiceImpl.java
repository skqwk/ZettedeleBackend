package ru.skqwk.zettedelebackend.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.event.EventRepo;
import ru.skqwk.zettedelebackend.event.EventService;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Описание класса
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;

    @Override
    public List<Event> loadMissingEvents(UserAccount userAccount, Map<UUID, HybridTimestamp> diffVectorVersion) {
        List<Event> events = eventRepo.findAllByUser(userAccount);

        List<Event> missingEvents = events.stream()
                .filter(e -> {
                    HybridTimestamp happenAt = HybridTimestamp.parse(e.getHappenAt());
                    UUID uuid = UUID.fromString(happenAt.getNodeId());
                    return Optional.ofNullable(diffVectorVersion.get(uuid))
                            .map(t -> t.happenedBefore(happenAt))
                            .orElse(Boolean.FALSE);
                })
                .collect(Collectors.toList());

        log.info("Load {} events for user with id = {}", missingEvents.size(), userAccount.getId());

        return missingEvents;
    }

    @Override
    public Event saveEvent(UserAccount user, Event event) {
        event.setUser(user);
        return eventRepo.save(event);
    }

    @Override
    public List<Event> getAllEvents(UserAccount userAccount) {
        return eventRepo.findAllByUser(userAccount);
    }

    @Override
    public void saveAllEvents(UserAccount userAccount, List<Event> events) {
        events.forEach(e -> e.setUser(userAccount));
        for (Event event : events) {
            try {
                eventRepo.save(event);
            } catch (DataIntegrityViolationException e) {
                log.info("This event {} already saved", event.getHappenAt());
            }
        }
    }
}
