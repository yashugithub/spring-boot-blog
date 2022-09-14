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

        if (!(userDetails instanceof TechTrainUser) &&
                (userDetails instanceof User) &&
                securityProperties.getUser().getName().equals(userDetails.getUsername()) &&
                securityProperties.getUser().getPassword().equals(authentication.getCredentials())) {
            System.out.println(userDetails.getUsername() + " logged in successfully using development mode!");
        } else {
            System.out.println(" user " + userDetails.getUsername() + " could not be logged in - incorrect user or password");

            //throw new BadCredentialsException("incorrect user or password");
            throw new BadCredentialsException(WRONG_AUTHENTICATION_MSG);
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
// support case insensitive login
        String lowerUser = username == null ? null : username.toLowerCase(Locale.ENGLISH);

        // if this is a management user (for spring-boot endpoints - return a standard user
        if (securityProperties.getUser().getName().equals(lowerUser)) {

            return new User(lowerUser, securityProperties.getUser().getPassword(), null);
        }

        System.out.println("this is a real user, fetching its details from the database");
        TechTrainUser user = techTrainUserService.readUserByUserName(lowerUser);
        if (user != null) {
            System.out.println("user {} loaded from database " + lowerUser);
            return null;
        }

        System.out.println("bad credentials, username: "+ lowerUser);

        //throw new BadCredentialsException("unknown user " + lowerUser);
        throw new BadCredentialsException(WRONG_AUTHENTICATION_MSG);    }
}
