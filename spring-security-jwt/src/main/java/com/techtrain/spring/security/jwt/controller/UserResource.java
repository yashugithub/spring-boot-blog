package com.techtrain.spring.security.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    @GetMapping("/user")
    public String getUserInformation() {
        return "Fetch user infromation from request";
    }
}
