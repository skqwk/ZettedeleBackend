package ru.skqwk.zettedelebackend.event.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

/**
 * Сущность для представления главного элемента синхронизации - события
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String happenAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    private EventType type;

    private String id;
    private String parentId;

    @Column(name = "payload")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> payload;


}
