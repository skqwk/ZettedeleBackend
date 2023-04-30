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
import ru.skqwk.zettedelebackend.user.domain.UserAccount;

import java.util.Map;
import java.util.UUID;

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
}
