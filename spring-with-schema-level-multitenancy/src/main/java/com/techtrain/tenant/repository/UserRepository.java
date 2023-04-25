package com.techtrain.tenant.repository;

import com.techtrain.tenant.entity.MTUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<MTUser, Long> {
}
