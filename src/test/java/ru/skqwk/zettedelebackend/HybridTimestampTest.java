package ru.skqwk.zettedelebackend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.skqwk.zettedelebackend.sync.clock.HybridTimestamp;

/**
 * Описание класса
 */
public class HybridTimestampTest {
    @Test
    void shouldToString() {
        // GIVEN
        HybridTimestamp hybridTimestamp = new HybridTimestamp(0, 0, "A");

        // WHEN
        String timestamp = hybridTimestamp.toString();

        // THEN
        String expected = "1970-01-01T00:00:00Z-0000-A";
        Assertions.assertEquals(expected, timestamp);
    }

    @Test
    void shouldParse() {
        // GIVEN
        HybridTimestamp expected = new HybridTimestamp(0, 0, "A");
        String timestamp = "1970-01-01T00:00:00Z-0000-A";

        // WHEN
        HybridTimestamp hybridTimestamp = HybridTimestamp.parse(timestamp);

        // THEN
        Assertions.assertEquals(0, expected.compareTo(hybridTimestamp));
    }
}
