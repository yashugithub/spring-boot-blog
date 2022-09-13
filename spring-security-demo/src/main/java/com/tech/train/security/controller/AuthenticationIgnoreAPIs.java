package com.tech.train.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Define the couple of restapi's which will pass through authentication without verify
 *  Define these api's in Security configuration with WebSecurityCustomizer web.ignoring()
 */
@RestController
public class AuthenticationIgnoreAPIs {

    @GetMapping("ignore1")
    public String ignore1(){
        return "This api is validated and ignore authentication";
    }
}
