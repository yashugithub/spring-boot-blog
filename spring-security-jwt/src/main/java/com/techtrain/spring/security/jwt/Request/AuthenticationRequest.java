package com.techtrain.spring.security.jwt.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest {

    private String userName;

    private String password;
}
