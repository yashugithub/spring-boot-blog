package com.tech.train.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {

    @GetMapping("/users/me")
    public String me(@AuthenticationPrincipal UserDetails userDetails){
        return "hello "+userDetails.getUsername();
    }
}
