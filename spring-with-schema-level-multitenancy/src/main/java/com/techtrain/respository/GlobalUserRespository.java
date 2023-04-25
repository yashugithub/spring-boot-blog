package com.techtrain.respository;

import com.techtrain.entity.GlobalMTUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalUserRespository extends JpaRepository<GlobalMTUser, Long> {
}
