package com.techtrain.mt.hibernate;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.techtrain.entity.Tenant;
import com.techtrain.respository.MTTenantRepository;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private static final long serialVersionUID = 600387902793121721L;
    private final String masterSchema;
    private final transient DataSource datasource;

    private final transient MTTenantRepository mtTenantRepository;
    private final Long maximumSize;
    private final Integer expireAfterAccess;

    private transient LoadingCache<String, String> tenantSchemas;

    @PostConstruct
    private void createCache() {
        tenantSchemas = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    public String load(String name) throws Exception {
                        Tenant tenant = mtTenantRepository.findByName(name);
                        if(Objects.nonNull(tenant))
                        {
                            if (tenant.getEnabled()) {
                                return tenant.getName();
                            } else if (!tenant.getEnabled()) {
                                throw new Exception("Unauthorized to access tenant: " + name);
                            }
                        }
                        throw new Exception("Tenant with the key " + name
                                + " doesn't exist in the system");
                    }
                });
    }

    @Autowired
    public SchemaBasedMultiTenantConnectionProvider(
            @Value("${multitenancy.parent.schema:#{null}}")
            String masterSchema,
            DataSource datasource,
            MTTenantRepository tenantRepository,
            @Value("${multitenancy.schema-cache.maximumSize}")
            Long maximumSize,
            @Value("${multitenancy.schema-cache.expireAfterAccess}")
            Integer expireAfterAccess) {
        this.masterSchema = masterSchema;
        this.datasource = datasource;
        this.mtTenantRepository = tenantRepository;
        this.maximumSize = maximumSize;
        this.expireAfterAccess = expireAfterAccess;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        log.info("Get connection for tenant {}", tenantIdentifier);
        String tenantSchema;
        try {
            tenantSchema = tenantSchemas.get(tenantIdentifier);
        } catch (ExecutionException e) {
            throw new RuntimeException("No such tenant: " + tenantIdentifier);
        }
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        log.info("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(masterSchema);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType) ) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}

