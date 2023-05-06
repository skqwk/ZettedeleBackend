package ru.skqwk.zettedelebackend.sync;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skqwk.zettedelebackend.sync.dto.EventDto;
import ru.skqwk.zettedelebackend.sync.dto.SyncRs;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.List;
import java.util.Map;

/**
 * API синхронизации
 */
public interface SyncApi {
    @PostMapping("/sync")
    SyncRs sync(@AuthenticationPrincipal UserAccount userAccount,
                @RequestBody Map<String, String> vectorVersion);

    @PostMapping("/share")
    ResponseEntity<?> share(@AuthenticationPrincipal UserAccount userAccount, @RequestBody List<EventDto> events);
}
