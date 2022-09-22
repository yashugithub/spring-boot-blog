package com.techtrain.spring.security.outh2.repository;

import com.techtrain.spring.security.outh2.entity.TechTrainUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TechTrainUser, Long> {

    TechTrainUser findByUsernameIgnoreCase(String userName);
}
