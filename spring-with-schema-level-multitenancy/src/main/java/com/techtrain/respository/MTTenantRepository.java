package com.techtrain.respository;

import com.techtrain.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MTTenantRepository extends CrudRepository<Tenant, Long> {
    Tenant findByName(String name);
}
