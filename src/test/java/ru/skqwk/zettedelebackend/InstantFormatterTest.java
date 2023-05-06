package ru.skqwk.zettedelebackend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantFormatterTest {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Test
    void format() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        Instant instant = Instant.ofEpochSecond(0);

        String formatted = formatter.format(instant);

        Assertions.assertEquals("1970-01-01 03:00:00", formatted);
    }
}
