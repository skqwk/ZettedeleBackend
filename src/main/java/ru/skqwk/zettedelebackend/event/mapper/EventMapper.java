package ru.skqwk.zettedelebackend.event.mapper;

import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.event.domain.EventType;
import ru.skqwk.zettedelebackend.sync.dto.EventDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Маппинг Event, EventDto
 */
public class EventMapper {
    public static List<Event> mapEventsDtoToDomain(List<EventDto> events) {
        return events.stream()
                .map(EventMapper::mapEventDtoTDomain)
                .collect(Collectors.toList());
    }

    public static Event mapEventDtoTDomain(EventDto event) {
        return Event.builder()
                .happenAt(event.getHappenAt())
                .payload(event.getPayload())
                .parentId(event.getParentId())
                .id(event.getId())
                .type(EventType.valueOf(event.getEvent()))
                .build();
    }

    public static List<EventDto> mapEventsDomainToDto(List<Event> events) {
        return events.stream()
                .map(EventMapper::mapEventDomainToDto)
                .collect(Collectors.toList());
    }

    public static EventDto mapEventDomainToDto(Event event) {
        return EventDto.builder()
                .happenAt(event.getHappenAt())
                .event(event.getType().toString())
                .payload(event.getPayload())
                .id(event.getId())
                .parentId(event.getParentId())
                .build();
    }
}
