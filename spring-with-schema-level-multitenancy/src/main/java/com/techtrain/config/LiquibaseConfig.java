package com.techtrain.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import liquibase.integration.spring.SpringLiquibase;

@Lazy(false)
@Configuration
@ConditionalOnProperty(name = "multitenancy.parent.liquibase.enabled", havingValue = "true", matchIfMissing = true)
public class LiquibaseConfig {

    @Value("${multitenancy.parent.schema:#{null}}")
    private String defaultSchema;

    @Bean
    @ConfigurationProperties("multitenancy.parent.liquibase")
    public LiquibaseProperties rootLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean(name="tenantLiquibaseProperties")
    @ConfigurationProperties("multitenancy.tenant.liquibase")
    public LiquibaseProperties tenantLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase liquibase(ObjectProvider<DataSource> liquibaseDataSource) {
        LiquibaseProperties liquibaseProperties = rootLiquibaseProperties();
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDefaultSchema(this.defaultSchema);
        liquibase.setDataSource(liquibaseDataSource.getIfAvailable());
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setLabels(liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        return liquibase;
    }
}
