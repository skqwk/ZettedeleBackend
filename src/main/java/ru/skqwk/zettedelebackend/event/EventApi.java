package ru.skqwk.zettedelebackend.event;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skqwk.zettedelebackend.sync.dto.EventDto;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;

/**
 * API для работы с событиями
 */
public interface EventApi {
    @GetMapping("/events/download")
    List<EventDto> getEvents(@AuthenticationPrincipal UserAccount userAccount);

    @PostMapping("/events/upload")
    ResponseEntity<?> saveEvents(@AuthenticationPrincipal UserAccount userAccount,
                                 @RequestBody List<EventDto> events);
}
