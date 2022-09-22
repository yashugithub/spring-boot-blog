package com.techtrain.spring.security.outh2.service;

import com.techtrain.spring.security.outh2.entity.TechTrainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class OAuth2SecurityUserService extends DefaultOAuth2UserService {

    @Autowired(required=true)
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return getValidOAuth2User(userRequest, oAuth2User);
        } catch (Exception e) {
            System.out.println("Exception while processing the oAuth2 user");
            throw new RuntimeException(e);
        }
    }

    private OAuth2User getValidOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws Exception {
        String oAuth2Username = oAuth2User.getAttribute("subject");
        if(Objects.isNull(oAuth2Username)) {
            throw new Exception("UserName is not found in oAuth2 provider");
        }

        TechTrainUser techTrainUser = userService.readUserWithAuthorities(oAuth2Username);
        if (techTrainUser != null) {
            if (!techTrainUser.isEnabled()) {
                throw new Exception(oAuth2Username + " user is disabled");
            } else if (!hasValidAuthorities(techTrainUser)) {
                throw new Exception("Insufficient permissions for user: " + oAuth2Username);
            }
            return techTrainUser;
        }

        throw new Exception("User information is not found");
    }

    private boolean hasValidAuthorities(TechTrainUser techTrainUser) {
       /* Collection<? extends GrantedAuthority> authorities = techTrainUser.getAuthorities();
        return !authorities.isEmpty();*/
        return true;
    }
}
