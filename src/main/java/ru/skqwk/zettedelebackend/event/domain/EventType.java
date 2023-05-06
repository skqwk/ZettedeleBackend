package ru.skqwk.zettedelebackend.event.domain;

/**
 * Основные типы событий в системе
 */
public enum EventType {
    CREATE_VAULT,
    UPDATE_VAULT,
    REMOVE_VAULT,

    CREATE_NOTE,
    UPDATE_NOTE,
    REMOVE_NOTE,

    CREATE_PARAGRAPH,
    REMOVE_PARAGRAPH,
    UPDATE_PARAGRAPH,

    ADD_LINK_NOTE,
    REMOVE_LINK_NOTE,
}
