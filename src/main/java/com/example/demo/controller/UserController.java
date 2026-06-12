package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUsers() {
        return "All Users";
    }

    @PostMapping
    public String createUser() {
        return "User Created";
    }
}
