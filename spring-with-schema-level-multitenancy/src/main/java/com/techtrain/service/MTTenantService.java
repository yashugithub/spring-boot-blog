package com.techtrain.service;

import com.techtrain.dto.CreateTenantDTO;
import com.techtrain.entity.Tenant;
import com.techtrain.respository.MTTenantRepository;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.mapping.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Objects;

@Service
public class MTTenantService {

    private final JdbcTemplate jdbcTemplate;
    private final LiquibaseProperties liquibaseProperties;
    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;
    @Autowired
    public MTTenantService(DataSource dataSource,
                             JdbcTemplate jdbcTemplate,
                             @Qualifier("tenantLiquibaseProperties")
                             LiquibaseProperties liquibaseProperties,
                             ResourceLoader resourceLoader) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.liquibaseProperties = liquibaseProperties;
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    MTTenantRepository mtTenantRepository;


    public boolean isTenantSchemaExists(String tenantName) {
        // check the tenant schema is exists.
        String schemaQuery = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";

        try {
            String tenantSchema = jdbcTemplate.queryForObject(schemaQuery, String.class, new Object[] { tenantName });
            return Objects.nonNull(tenantSchema);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Boolean createTenant(CreateTenantDTO createTenantDTO) {

        Tenant tenant = new Tenant();
        tenant.setType(createTenantDTO.getType());
        tenant.setName(createTenantDTO.getName());
        mtTenantRepository.save(tenant);

        Boolean isTenantSchemaCreated = createNewTenantSchema(createTenantDTO.getName());

        //if(isTenantSchemaCreated){
            // create tables for new tenant schema using spring liquibase;
            try {
                runTenantSchemaLiquibase(createTenantDTO.getName(), dataSource);
            } catch (LiquibaseException e) {
                throw new RuntimeException(e);
            }
       // }

        return false;
    }


    protected SpringLiquibase getTenantSchemaLiquibase(String tenantSchema, DataSource mtDataSource) {
        SpringLiquibase tenantLiquibase = new SpringLiquibase();
        tenantLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        tenantLiquibase.setResourceLoader(resourceLoader);
        tenantLiquibase.setDefaultSchema(tenantSchema);
        tenantLiquibase.setDataSource(mtDataSource);
        tenantLiquibase.setContexts(liquibaseProperties.getContexts());
        tenantLiquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        tenantLiquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        tenantLiquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        tenantLiquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        tenantLiquibase.setDropFirst(liquibaseProperties.isDropFirst());
        tenantLiquibase.setShouldRun(liquibaseProperties.isEnabled());
        tenantLiquibase.setLabels(liquibaseProperties.getLabels());
        tenantLiquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        tenantLiquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        tenantLiquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        return tenantLiquibase;
    }

    private void runTenantSchemaLiquibase(String tenantSchema, DataSource dataSource) throws LiquibaseException {
        SpringLiquibase liquibase = getTenantSchemaLiquibase(tenantSchema, dataSource);
        liquibase.afterPropertiesSet();
    }

    private Boolean createNewTenantSchema(String tenantSchemaName) {
        return jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
                stmt.execute("CREATE SCHEMA " + "\"" + tenantSchemaName + "\""));

    }
}
