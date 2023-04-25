package com.techtrain.controller;

import com.techtrain.dto.CreateUserDTO;
import com.techtrain.service.MTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class MTUserController {

    @Autowired
    MTUserService mtUserService;

    @GetMapping
    public ResponseEntity<String> getAllUsers(){
        return ResponseEntity.ok("Successfully called");
    }


    @PostMapping
    public ResponseEntity<String> createPerTenantBasedUser(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        mtUserService.createUserPerTenant(createUserDTO);

        return new ResponseEntity("Created user in per tenant schema!", HttpStatus.CREATED);

    }
}
