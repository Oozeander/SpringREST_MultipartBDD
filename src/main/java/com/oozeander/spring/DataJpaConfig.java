/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.spring;

import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author b.ketrouci
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.oozeander.repository"})
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class DataJpaConfig {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean(destroyMethod = "close")
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.user"));
        dataSource.setPassword(env.getRequiredProperty("db.pass"));
        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaProperties(jpaProperties());
        entityManagerFactory.setPackagesToScan("com.oozeander.model");
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return entityManagerFactory;
    }

    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.putAll(Map.of(Environment.DIALECT, env.getRequiredProperty("db.dialect"),
                Environment.HBM2DDL_AUTO, env.getRequiredProperty("db.hbm2ddl_auto"),
                Environment.SHOW_SQL, env.getRequiredProperty("db.show_sql"),
                Environment.FORMAT_SQL, env.getRequiredProperty("db.format_sql"),
                Environment.ENABLE_LAZY_LOAD_NO_TRANS, env.getRequiredProperty("db.enable_lazy_load_no_trans")));
        return jpaProperties;
    }

    @Bean
    PlatformTransactionManager transactionManager(final LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
