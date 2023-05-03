package ru.skqwk.zettedelebackend.event.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.event.EventApi;
import ru.skqwk.zettedelebackend.event.EventService;
import ru.skqwk.zettedelebackend.event.domain.Event;
import ru.skqwk.zettedelebackend.sync.dto.EventDto;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;

import static ru.skqwk.zettedelebackend.event.mapper.EventMapper.mapEventsDomainToDto;
import static ru.skqwk.zettedelebackend.event.mapper.EventMapper.mapEventsDtoToDomain;

/**
 * Реализация сохранения событий
 */
@RestController
@RequiredArgsConstructor
public class EventController implements EventApi {
    private final EventService eventService;

    @Override
    public List<EventDto> getEvents(UserAccount userAccount) {
        List<Event> events = eventService.getAllEvents(userAccount);
        return mapEventsDomainToDto(events);
    }

    @Override
    public ResponseEntity<?> saveEvents(UserAccount userAccount, List<EventDto> events) {
        List<Event> eventsForSave = mapEventsDtoToDomain(events);
        eventService.saveAllEvents(userAccount, eventsForSave);
        return ResponseEntity.ok().build();
    }
}
