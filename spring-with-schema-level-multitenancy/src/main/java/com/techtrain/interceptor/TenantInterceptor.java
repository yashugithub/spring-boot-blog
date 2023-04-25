package com.techtrain.interceptor;

import java.util.Objects;

import com.techtrain.mt.hibernate.MTTenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import lombok.NonNull;

@Component
public class TenantInterceptor implements WebRequestInterceptor {

    private final String defaultTenant;

    private static final String  TENANT_AUTH_HEADER = "ACCEPTED-TENANT";

    @Autowired
    public TenantInterceptor(
            @Value("${multitenancy.parent.schema:#{null}}")
            String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    public void preHandle(WebRequest request) {
        String tenantId;
        if (Objects.nonNull(request.getHeader(TENANT_AUTH_HEADER))) {
            tenantId = request.getHeader(TENANT_AUTH_HEADER).equals("_shared")
                    ? "public" : request.getHeader(TENANT_AUTH_HEADER);
        } else {
            tenantId = this.defaultTenant;
        }
        MTTenantContext.setTenantId(tenantId);
    }

    @Override
    public void postHandle(@NonNull WebRequest request, ModelMap model) {
        MTTenantContext.clear();
    }

    @Override
    public void afterCompletion(@NonNull WebRequest request, Exception ex) {
        // NOOP
    }
}
