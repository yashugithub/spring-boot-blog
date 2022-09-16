package com.tech.train.security.config;

import com.tech.train.security.entity.TechTrainUser;
import com.tech.train.security.service.TechTrainUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class is used to fetch the user information from Datasource
 * and authenticate the user from request with some more checks.
 *
 * This will be a global authentication provider for all the request, hence this should be register to authentication manager and should
 * be executed with high precedence
 */
@Component
public class CustomUserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    String WRONG_AUTHENTICATION_MSG = "Wrong user information. Try again!";
    @Autowired
    TechTrainUserService techTrainUserService;

    @Autowired
    SecurityProperties securityProperties;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        /*System.out.println(securityProperties.getUser().getName());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());
        System.out.println(authentication.getCredentials());
        System.out.println(userDetails instanceof TechTrainUser);*/

        if ((userDetails instanceof TechTrainUser) &&
                userDetails.getUsername().equals(userDetails.getUsername()) &&
                userDetails.getPassword().equals(authentication.getCredentials())) {
            System.out.println(userDetails.getUsername() + " login successful!");
        } else {
            System.out.println(" user " + userDetails.getUsername() + " could not be logged in - incorrect user or password");
            throw new BadCredentialsException(WRONG_AUTHENTICATION_MSG);
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String lowerUser = username == null ? null : username.toLowerCase(Locale.ENGLISH);

       /* System.out.println("Testing ---> "+lowerUser);
        System.out.println("securityProperties ---> "+securityProperties.getUser().getName());*/

        // if this is a management user (for spring-boot endpoints - return a standard user
        if (securityProperties.getUser().getName().equals(lowerUser)) {

            return new User(lowerUser, securityProperties.getUser().getPassword(), new ArrayList<>());
        }

        System.out.println("this is a real user, fetching its details from the database");
        TechTrainUser user = techTrainUserService.readUserByUserName(lowerUser);
       // System.out.println("user object-- "+user);
        if (user != null) {
            System.out.println("user {} loaded from database " + lowerUser);
            //System.out.println("-------------user ------ "+ user.getUsername());
            return user;
        }

        System.out.println("bad credentials, username: "+ lowerUser);
        throw new BadCredentialsException(WRONG_AUTHENTICATION_MSG);    }
}
