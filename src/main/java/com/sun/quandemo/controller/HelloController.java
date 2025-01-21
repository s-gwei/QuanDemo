package com.sun.quandemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping("/hello")
    public String hello() {
        logger.info("Hello endpoint called");
        return "Hello from HelloController!";
    }
    
    @GetMapping("/status")
    public String status() {
        logger.info("Status endpoint called");
        return "Service is running";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable String id) {
        logger.info("Getting user with id: {}", id);
        return "User info for ID: " + id;
    }

    @PostMapping("/message")
    public String postMessage(@RequestBody String message) {
        logger.info("Received message: {}", message);
        return "Message received: " + message;
    }

    @GetMapping("/test")
    public String test() {
        logger.info("Test endpoint called");
        return "This is a test endpoint";
    }
} 