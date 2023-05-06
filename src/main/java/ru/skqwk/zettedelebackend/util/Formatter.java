package ru.skqwk.zettedelebackend.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Вспомогательный класс для форматирования дат
 */
public class Formatter {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    public static String format(Instant instant) {
        return formatter.format(instant);
    }
}
