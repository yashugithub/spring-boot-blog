package com.techtrain.mt.hibernate;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MTTenantContext {

    private MTTenantContext() {}

    private static final InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }
}

