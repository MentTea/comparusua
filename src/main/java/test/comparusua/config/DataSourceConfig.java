package test.comparusua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import test.comparusua.config.property.DataSourceInfo;
import test.comparusua.config.property.DataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        for (DataSourceInfo sourceConfig : dataSourceProperties.getDataSources()) {
            targetDataSources.put(sourceConfig.getName(), dataSourceMapping(sourceConfig));
        }
        dynamicRoutingDataSource.setTargetDataSources(targetDataSources);
        return dynamicRoutingDataSource;
    }

    public DataSource dataSourceMapping(DataSourceInfo dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUser());
        dataSource.setPassword(dataSourceProperties.getPassword());
        return dataSource;
    }
}