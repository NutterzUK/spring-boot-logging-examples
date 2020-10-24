package com.example.demo.controllers;

import com.example.demo.rule.LogVerifier;
import org.junit.Rule;
import org.junit.Test;

public class HealthControllerTest {

    @Rule
    public final LogVerifier logVerifier = new LogVerifier();

    @Test
    public void whenHealthCheckCalledThenLogsMessage() {
        HealthController sut = new HealthController();
        sut.checkHealth();

        logVerifier.verifyMessage("My message set at info level");
    }
}