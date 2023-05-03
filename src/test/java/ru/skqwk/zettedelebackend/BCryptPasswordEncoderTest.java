package ru.skqwk.zettedelebackend;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BCryptPasswordEncoderTest {

    @Test
    void encode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode("ADMIN");

        System.out.println(encoded);

        assertTrue(encoder.matches("ADMIN", encoded));
    }

}
