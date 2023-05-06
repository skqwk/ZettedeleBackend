package ru.skqwk.zettedelebackend.sync.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * DTO для представления события
 */
@Getter
@Builder
public class EventDto {
    String event;
    String happenAt;
    Map<String, String> payload;
    String id;
    String parentId;
}
