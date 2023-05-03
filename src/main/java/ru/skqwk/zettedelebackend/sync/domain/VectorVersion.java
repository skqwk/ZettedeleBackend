package ru.skqwk.zettedelebackend.sync.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Вектор версий
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "vector_version")
@AllArgsConstructor
@NoArgsConstructor
public class VectorVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "vector")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> vector;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    public static Map<UUID, HybridTimestamp> toDomain(Map<String, String> vectorVersionDto) {
        return vectorVersionDto.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> UUID.fromString(e.getKey()),
                        e -> HybridTimestamp.parse(e.getValue())));
    }

    public static Map<String, String> toDto(Map<UUID, HybridTimestamp> vectorVersionDomain) {
        return vectorVersionDomain.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString()));
    }
}
