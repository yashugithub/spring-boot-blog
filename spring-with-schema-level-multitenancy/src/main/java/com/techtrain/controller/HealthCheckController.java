package com.techtrain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mt/healthcheck")
public class HealthCheckController {

    @GetMapping
    public String healtCheckStatus(){
        return "Alive!";
    }
}
