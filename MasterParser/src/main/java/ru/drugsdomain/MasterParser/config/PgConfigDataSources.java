package ru.drugsdomain.MasterParser.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@Data
@PropertySource("classpath:datasources.properties")
public class PgConfigDataSources {

    @Value("${datasource.pg.driverClassName}")
    private String driverClassName;

    @Value("${datasource.pg.url}")
    private String url;

    @Value("${datasource.pg.username}")
    private String username;

    @Value("${datasource.pg.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

}
