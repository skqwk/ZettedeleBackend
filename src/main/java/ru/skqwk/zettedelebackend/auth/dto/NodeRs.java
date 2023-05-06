package ru.skqwk.zettedelebackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Тело ответа для получения идентификатора узла
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeRs {
    String nodeId;
}
