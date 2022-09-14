package com.tech.train.security.jpa.repository;

import com.tech.train.security.entity.TechTrainUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechTrainUserRepository extends JpaRepository<TechTrainUser, Long> {

    TechTrainUser findByUsernameIgnoreCase(String userName);
}
