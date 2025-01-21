package com.sun.quandemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class HelloController {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    
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