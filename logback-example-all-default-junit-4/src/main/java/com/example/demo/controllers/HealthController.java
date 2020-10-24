package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/health")
public class HealthController {

    private static final Logger LOG = LoggerFactory.getLogger(HealthController.class);

    @GetMapping
    public ResponseEntity<String> checkHealth(){
        LOG.info("My message set at info level");
        return ResponseEntity.ok("Healthy");
    }
}
