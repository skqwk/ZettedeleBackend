package ru.skqwk.zettedelebackend;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

    @Test
    void testUUIDCompatibility() {
        UUID reactUUID = UUID.fromString("556a4927-b728-4c90-8d87-f14f26a2b5ee");
        UUID javaUUID = UUID.randomUUID();

        System.out.println(reactUUID);
        System.out.println(javaUUID);
    }
}
