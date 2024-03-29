package com.techtrain.mt.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component("currentTenantIdentifierResolver")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Value("${multitenancy.parent.schema:#{null}}")
    String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = MTTenantContext.getTenantId();
        if (!ObjectUtils.isEmpty(tenantId)) {
            return tenantId;
        } else {
            // Allow bootstrapping the EntityManagerFactory, in which case no tenant is needed
            return "BOOTSTRAP";
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
