package ru.skqwk.zettedelebackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO для представления данных, просматриваемых администратором
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountAdminViewDto {
    private String login;
    private String visibility;
    private String lastAuth;
}
