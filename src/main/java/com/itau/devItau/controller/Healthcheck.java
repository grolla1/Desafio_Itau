package com.itau.devItau.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Healthcheck {
    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        logger.info("======= CONTROLLER: GET /health =======");
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
