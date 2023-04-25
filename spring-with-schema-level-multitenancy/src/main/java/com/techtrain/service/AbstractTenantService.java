package com.techtrain.service;

import com.techtrain.mt.hibernate.MTTenantContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class AbstractTenantService {

    @Autowired
    MTTenantService tenantService;


    public boolean isValidTenant(String tenantName) throws Exception {
        if(Objects.nonNull(tenantService.isTenantSchemaExists(tenantName)))
        {
            return true;
        }
        throw new Exception("The tenant with name '" + tenantName + "' doesn't exist in the system");
    }

    public boolean isHeaderHasTenant(String tenantName) throws Exception {
        String headerTenantKey = MTTenantContext.getTenantId();
        if (headerTenantKey.equals(tenantName)) {
            return true;
        } else if (headerTenantKey.equals("public")) {
            throw new Exception("The header tenant information is missing");
        } else {
            throw new Exception("The tenant name '" + tenantName +
                    "' in url doesn't match with header tenant key '" + headerTenantKey +"'");
        }
    }
}
