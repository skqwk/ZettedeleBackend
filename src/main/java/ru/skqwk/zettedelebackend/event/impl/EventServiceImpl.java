package ru.skqwk.zettedelebackend.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.skqwk.zettedelebackend.event.EventRepo;
import ru.skqwk.zettedelebackend.event.EventService;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.sync.VectorVersionService;
import ru.skqwk.zettedelebackend.sync.clock.HLC;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.sync.domain.VectorVersion;
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
    private final VectorVersionService vectorVersionService;

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

        log.info("Загрузка {} событий для пользователя {}", missingEvents.size(), userAccount.getLogin());

        return missingEvents;
    }

    @Override
    public void saveEvent(UserAccount user, Event event) {
        event.setUser(user);
        try {
            eventRepo.save(event);
        } catch (DataIntegrityViolationException ex) {
            log.info("Событие с happenedAt = {} уже сохранено", event.getHappenAt());
        }
    }

    @Override
    public List<Event> getAllEvents(UserAccount userAccount) {
        return eventRepo.findAllByUser(userAccount);
    }

    @Override
    public void saveAllEvents(UserAccount userAccount, List<Event> events) {
        VectorVersion vector = vectorVersionService.findVectorVersionByUser(userAccount);
        Map<UUID, HybridTimestamp> vectorVersion = VectorVersion.toDomain(vector.getVector());

        HybridTimestamp serverLatestTimestamp = vectorVersion.get(userAccount.getId());
        HLC hlc = new HLC(serverLatestTimestamp.getWallClockTime(), serverLatestTimestamp.getNodeId());
        for (Event event : events) {
            HybridTimestamp remoteTimestamp = HybridTimestamp.parse(event.getHappenAt());
            saveEvent(userAccount, event);
            hlc.tick(remoteTimestamp);
        }
        HybridTimestamp latestTime = hlc.getLatestTime();
        vectorVersion.put(userAccount.getId(), latestTime);
        vectorVersionService.updateVectorVersion(vector, vectorVersion);
    }
}
