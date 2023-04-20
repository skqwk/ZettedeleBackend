package ru.skqwk.zettedelebackend.healthcheck.impl;

import org.springframework.web.bind.annotation.RestController;
import ru.skqwk.zettedelebackend.healthcheck.PingApi;

@RestController
public class PingController implements PingApi {
    @Override
    public String ping() {
        return "pong";
    }
}
