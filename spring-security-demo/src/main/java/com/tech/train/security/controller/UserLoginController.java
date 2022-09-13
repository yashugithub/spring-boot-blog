package com.tech.train.security.controller;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserLoginController {

    @GetMapping("/users/security/principal")
    public String getUserByPrincipal(@AuthenticationPrincipal UserDetails userDetails){
        return "Fetch user information using authentication principal : "+userDetails.getUsername();
    }


    @GetMapping("/users/security/context")
    public String getUserBySecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // before returning user information check that user is an authenticated user
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return "Fetch the user information using Security context : " + authentication.getName();
        }
        return "User information is not able to fetch as user is anonymous";
    }

    @GetMapping("/users/security/servlet")
    public String getUserByServletRequest(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return "Fetch user information from HttpServletRequest :: " + principal.getName();
    }
}
