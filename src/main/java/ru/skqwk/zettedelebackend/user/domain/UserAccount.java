package ru.skqwk.zettedelebackend.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

/**
 * Сущность для представления аккаунтов в системе.
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "user_account")
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount extends AbstractUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String password;

    private Instant lastAuth;

    @Column(unique = true)
    private String login;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserAccountVisibility visibility = UserAccountVisibility.PUBLIC;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return login;
    }
}
