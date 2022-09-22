package com.techtrain.spring.security.outh2.converter;

import com.techtrain.spring.security.outh2.entity.TechTrainUser;
import com.techtrain.spring.security.outh2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * This class is used to convert the JWT token and validate the user.
 *
 */
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Autowired
    private UserService userService;

    public JwtAuthenticationConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwtToken) {
        String userName = jwtToken.getClaimAsString("subject");
        TechTrainUser user = userService.findUserByUserName(userName);

        // Convert the JWT token into user password information and proceed with authentication and authorization

        return new UsernamePasswordAuthenticationToken(user, "n/a", user.getAuthorities());
    }
}
