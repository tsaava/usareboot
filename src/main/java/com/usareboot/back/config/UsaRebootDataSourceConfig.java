package com.usareboot.back.config;

//import com.nexign.esb.config.security.AESUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.usareboot.back.persistence.usareboot"},
        entityManagerFactoryRef = "usarebootEntityManagerFactory",
        transactionManagerRef = "usarebootTransactionManager"
)
@RequiredArgsConstructor
public class UsaRebootDataSourceConfig {
    @Value("${spring.datasource.usareboot.hibernate.dialect}")
    private String dialect;
    @Value("${spring.datasource.usareboot.password}")
    private String password;

    @Value("${spring.datasource.usareboot.schema}")
    private String schema;

    @Bean
    public LocalContainerEntityManagerFactoryBean usarebootEntityManagerFactory(
            @Qualifier("usarebootDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", dialect);
        jpaProperties.put("hibernate.ddl-auto", "none");
        jpaProperties.put("hibernate.default_schema", schema);
        jpaProperties.put("open-in-view", "false");
        return builder
                .dataSource(dataSource)
                .packages("com.usareboot.back.persistence.usareboot")
                .persistenceUnit("usarebootDataSource")
                .properties(jpaProperties)
                .build();
    }

    @Bean
    public PlatformTransactionManager usarebootTransactionManager(
            @Qualifier("usarebootEntityManagerFactory") LocalContainerEntityManagerFactoryBean usarebootEntityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(usarebootEntityManagerFactory.getObject()));
    }

    @Bean
    @ConfigurationProperties("spring.datasource.usareboot.hikari")
    public DataSource usarebootDataSource() {
//        usarebootDataSourceProperties().setPassword(AESUtil.decryptPasswordBased(password));
        usarebootDataSourceProperties().setPassword(password);
        return usarebootDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.usareboot")
    public DataSourceProperties usarebootDataSourceProperties() {
        return new DataSourceProperties();
    }
}