package com.example.demo.controllers;

import com.example.demo.extension.LoggerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class HealthControllerTest {

    @RegisterExtension
    public static LoggerExtension logVerifier = new LoggerExtension();

    @Test
    public void whenHealthCheckCalledThenLogsMessage() {
        HealthController sut = new HealthController();
        sut.checkHealth();

        logVerifier.verifyMessage("My message set at info level");
    }
}