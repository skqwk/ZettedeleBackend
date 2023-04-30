package ru.skqwk.zettedelebackend.sync.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.event.EventService;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.event.domain.EventType;
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

        Map<UUID, HybridTimestamp> localVectorVersion = mapVectorVersionDtoToDomain(local.getVector());

        Map<UUID, HybridTimestamp> remoteVectorVersion = mapVectorVersionDtoToDomain(vectorVersionDto);
        Map<UUID, HybridTimestamp> diff = vectorVersionService.diffVectorVersion(localVectorVersion, remoteVectorVersion);

        List<Event> events = eventService.loadMissingEvents(userAccount, diff);

        Map<UUID, HybridTimestamp> merge = vectorVersionService.mergeVectorVersion(localVectorVersion,
                remoteVectorVersion);

        vectorVersionService.updateVectorVersion(local, merge);

        return SyncRs.builder()
                .events(mapEventsDomainToDto(events))
                .vectorVersion(mapVectorVersionDomainToDto(localVectorVersion))
                .build();
    }

    private Map<UUID, HybridTimestamp> fromString(Map<String, String> vector) {
        return vector.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> UUID.fromString(e.getKey()),
                        e -> HybridTimestamp.parse(e.getValue())));
    }

    @Override
    public ResponseEntity<?> share(UserAccount userAccount, List<EventDto> eventsDto) {
        List<Event> events = mapEventsDtoToDomain(eventsDto);
        VectorVersion vector = vectorVersionService.findVectorVersionByUser(userAccount);
        Map<UUID, HybridTimestamp> vectorVersion = fromString(vector.getVector());

        HybridTimestamp serverLatestTimestamp = vectorVersion.get(userAccount.getId());
        HLC hlc = new HLC(serverLatestTimestamp.getWallClockTime(), serverLatestTimestamp.getNodeId());
        for (Event event : events) {
            HybridTimestamp remoteTimestamp = HybridTimestamp.parse(event.getHappenAt());
            eventService.saveEvent(userAccount, event);
            hlc.tick(remoteTimestamp);
        }
        HybridTimestamp latestTime = hlc.getLatestTime();
        vectorVersion.put(userAccount.getId(), latestTime);
        vectorVersionService.updateVectorVersion(vector, vectorVersion);
        return ResponseEntity.ok().build();
    }

    private List<Event> mapEventsDtoToDomain(List<EventDto> events) {
        return events.stream()
                .map(this::mapEventDtoTDomain)
                .collect(Collectors.toList());
    }

    private Event mapEventDtoTDomain(EventDto event) {
        return Event.builder()
                .happenAt(event.getHappenAt())
                .payload(event.getPayload())
                .parentId(event.getParentId())
                .id(event.getId())
                .type(EventType.valueOf(event.getEvent()))
                .build();
    }

    private List<EventDto> mapEventsDomainToDto(List<Event> events) {
        return events.stream()
                .map(this::mapEventDomainToDto)
                .collect(Collectors.toList());
    }

    private EventDto mapEventDomainToDto(Event event) {
        return EventDto.builder()
                .happenAt(event.getHappenAt())
                .event(event.getType().toString())
                .payload(event.getPayload())
                .id(event.getId())
                .parentId(event.getParentId())
                .build();
    }

    private Map<UUID, HybridTimestamp> mapVectorVersionDtoToDomain(Map<String, String> vectorVersionDto) {
        return vectorVersionDto.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> UUID.fromString(e.getKey()),
                        e -> HybridTimestamp.parse(e.getValue())));
    }

    private Map<String, String> mapVectorVersionDomainToDto(Map<UUID, HybridTimestamp> vectorVersionDomain) {
        return vectorVersionDomain.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString()));
    }
}
