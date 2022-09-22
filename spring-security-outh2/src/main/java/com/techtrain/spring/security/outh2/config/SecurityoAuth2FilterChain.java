package com.techtrain.spring.security.outh2.config;

import com.techtrain.spring.security.outh2.converter.JwtAuthenticationConverter;
import com.techtrain.spring.security.outh2.service.OAuth2SecurityUserService;
import com.techtrain.spring.security.outh2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@EnableWebSecurity
@Configuration
public class SecurityoAuth2FilterChain {

    @Autowired
    private OAuth2SecurityUserService oAuth2SecurityUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2FailureHandler oAuth2FailureHandler;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter(userService);
    }

    /**
     * These are the website directives to clear when the session expired or logout
     */
    private static final ClearSiteDataHeaderWriter.Directive[] DIRECTIVES =
            {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) ->
                auth.anyRequest().authenticated())
                .oauth2Login()
                    .authorizationEndpoint().baseUri("/oauth2/authorization/keycloak").and()
                    .redirectionEndpoint().baseUri("/callback").and()
                    .userInfoEndpoint().userService(oAuth2SecurityUserService).and()
                .and()
                    .oauth2ResourceServer()
                    .jwt().jwtAuthenticationConverter(this.jwtAuthenticationConverter()).and()
                .and().csrf().disable();

        // Handle the success and failure calls

        //http.oauth2Login();

        /*http.oauth2Login().loginPage("/oauth2/authorization/")
                .failureHandler(oAuth2FailureHandler)
                .successHandler(oAuth2SuccessHandler)
                .and().logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/logout")
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(DIRECTIVES)));
*/
        return http.build();
    }
}
