package ru.skqwk.zettedelebackend.sync.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Ответ на синхронизационный запрос
 */
@Getter
@Builder
public class SyncRs {
    Map<String, String> vectorVersion;
    List<EventDto> events;
}
