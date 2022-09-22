package com.techtrain.spring.security.outh2.service;

import com.techtrain.spring.security.outh2.entity.TechTrainUser;
import com.techtrain.spring.security.outh2.repository.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Data
@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public TechTrainUser findUserByUserName(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName);
    }

    public TechTrainUser readUserWithAuthorities(String oAuth2Username) {
        TechTrainUser techTrainUser = userRepository.findByUsernameIgnoreCase(oAuth2Username);
        if (techTrainUser == null) {
            return null;
        }

        // Pending Add roles to user authorities

        return techTrainUser;
    }
}
