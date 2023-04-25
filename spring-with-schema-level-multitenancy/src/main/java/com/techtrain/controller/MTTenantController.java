package com.techtrain.controller;

import com.techtrain.dto.CreateTenantDTO;
import com.techtrain.service.MTTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenant")
public class MTTenantController {

    @Autowired
    MTTenantService mtTenantService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateTenantDTO createTenantDTO) {
        mtTenantService.createTenant(createTenantDTO);
        return new ResponseEntity<>("Tenant Created", HttpStatus.CREATED);
    }
}
