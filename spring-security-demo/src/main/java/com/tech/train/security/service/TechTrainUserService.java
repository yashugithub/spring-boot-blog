package com.tech.train.security.service;

import com.tech.train.security.entity.TechTrainUser;
import com.tech.train.security.jpa.repository.TechTrainUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechTrainUserService {

    @Autowired
    TechTrainUserRepository techTrainUserRepository;

    public TechTrainUser readUserByUserName(String userName) {
        TechTrainUser user = techTrainUserRepository.findByUsernameIgnoreCase(userName);
        return user;
    }
}
