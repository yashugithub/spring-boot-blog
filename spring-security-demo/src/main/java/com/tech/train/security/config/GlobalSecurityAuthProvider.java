package com.tech.train.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Register the authentication provider to Authentication Manager
 *
 */
@Order(HIGHEST_PRECEDENCE)
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Configuration
public class GlobalSecurityAuthProvider extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    CustomUserAuthenticationProvider customUserAuthenticationProvider;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customUserAuthenticationProvider);
    }
}
