package com.techtrain.spring.security.jwt.controller;

import com.techtrain.spring.security.jwt.Request.AuthenticationRequest;
import com.techtrain.spring.security.jwt.Response.AuthenticationResponse;
import com.techtrain.spring.security.jwt.service.CustomUserDetailsService;
import com.techtrain.spring.security.jwt.util.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTAuthenticateResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTutil jwTutil;

    @GetMapping("/hello")
    public String hello(){
        return "hello Yashodha";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateJWTToken(@RequestBody AuthenticationRequest request) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Incorrect credentials");
        }

        final UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(request.getUserName());
        final String jwtToken = jwTutil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
