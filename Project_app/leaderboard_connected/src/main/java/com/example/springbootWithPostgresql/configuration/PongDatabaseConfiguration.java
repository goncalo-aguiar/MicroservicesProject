package com.example.springbootWithPostgresql.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        entityManagerFactoryRef = "pongEntityManagerFactory",
        transactionManagerRef = "pongTransactionManager",
        basePackages = "com.example.springbootWithPostgresql.dao.pong"
)
public class PongDatabaseConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean pongEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(pongDataSource());
        em.setPackagesToScan(
                new String[] { "com.example.springbootWithPostgresql.model.pong" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                "update");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public DataSource pongDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        //dataSource.setDriverClassName(
        //         env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pong");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager pongTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                pongEntityManagerFactory().getObject());
        return transactionManager;
    }
}

//    @Bean
//    public DataSourceProperties pongDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "pongDataSource")
//    @ConfigurationProperties(prefix = "spring.second-datasource")
//    public DataSource pongDataSource() {
//        return pongDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//    @Bean(name = "pongEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean pongEntityManagerFactory(
//            final EntityManagerFactoryBuilder builder,
//            final @Qualifier("pongDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages(PongUserEntity.class)
//                .persistenceUnit("pong")
//                .build();
//    }
//
//    @Bean(name = "pongTransactionManager")
//    public PlatformTransactionManager pongTransactionManager(@Qualifier("pongEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
