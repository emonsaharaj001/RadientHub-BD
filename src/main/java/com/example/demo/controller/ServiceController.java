package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @GetMapping
    public String getServices() {
        return "All Services";
    }

    @PostMapping
    public String createService() {
        return "Service Created";
    }
}
