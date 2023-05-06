package ru.skqwk.zettedelebackend.sync.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.event.EventService;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.event.domain.EventType;
import ru.skqwk.zettedelebackend.event.mapper.EventMapper;
import ru.skqwk.zettedelebackend.sync.SyncApi;
import ru.skqwk.zettedelebackend.sync.VectorVersionService;
import ru.skqwk.zettedelebackend.sync.clock.HLC;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.sync.domain.VectorVersion;
import ru.skqwk.zettedelebackend.sync.dto.EventDto;
import ru.skqwk.zettedelebackend.sync.dto.SyncRs;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.skqwk.zettedelebackend.event.mapper.EventMapper.*;
import static ru.skqwk.zettedelebackend.event.mapper.EventMapper.mapEventsDtoToDomain;

/**
 * Реализация контроллера для синхронизации
 */
@RestController
@RequiredArgsConstructor
public class SyncController implements SyncApi {
    private final VectorVersionService vectorVersionService;
    private final EventService eventService;

    @Override
    public SyncRs sync(UserAccount userAccount, Map<String, String> vectorVersionDto) {

        VectorVersion local = vectorVersionService.findVectorVersionByUser(userAccount);

        Map<UUID, HybridTimestamp> localVectorVersion = VectorVersion.toDomain(local.getVector());

        Map<UUID, HybridTimestamp> remoteVectorVersion = VectorVersion.toDomain(vectorVersionDto);
        Map<UUID, HybridTimestamp> diff = vectorVersionService.diffVectorVersion(localVectorVersion, remoteVectorVersion);

        List<Event> events = eventService.loadMissingEvents(userAccount, diff);

        Map<UUID, HybridTimestamp> merge = vectorVersionService.mergeVectorVersion(localVectorVersion,
                remoteVectorVersion);

        vectorVersionService.updateVectorVersion(local, merge);

        return SyncRs.builder()
                .events(mapEventsDomainToDto(events))
                .vectorVersion(VectorVersion.toDto(localVectorVersion))
                .build();
    }

    @Override
    public ResponseEntity<?> share(UserAccount userAccount, List<EventDto> eventsDto) {
        List<Event> events = mapEventsDtoToDomain(eventsDto);
        eventService.saveAllEvents(userAccount, events);
        return ResponseEntity.ok().build();
    }
}
