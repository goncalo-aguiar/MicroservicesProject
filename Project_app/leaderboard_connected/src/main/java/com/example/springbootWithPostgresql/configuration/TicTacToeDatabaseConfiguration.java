package com.example.springbootWithPostgresql.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource({ "classpath:application.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ticTacToeEntityManagerFactory",
        transactionManagerRef = "ticTacToeTransactionManager",
        basePackages = "com.example.springbootWithPostgresql.dao.tictactoe"
)
public class TicTacToeDatabaseConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean ticTacToeEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(tictactoeDataSource());
        em.setPackagesToScan(
                new String[] { "com.example.springbootWithPostgresql.model.tictactoe" });

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
    public DataSource tictactoeDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        //dataSource.setDriverClassName(
       //         env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tictactoe");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager ticTacToeTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                ticTacToeEntityManagerFactory().getObject());
        return transactionManager;
    }
}

//    @Bean
//    @Primary
//    public DataSourceProperties ticTacToeDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "ticTacToeDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource ticTacToeDataSource() {
//        return ticTacToeDataSourceProperties().initializeDataSourceBuilder().build();
//    }
//
//    @Bean(name = "ticTacToeEntityManagerFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean ticTacToeEntityManagerFactory(
//            final EntityManagerFactoryBuilder builder,
//            final @Qualifier("ticTacToeDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages(TicTacToeUserEntity.class)
//                .persistenceUnit("tictactoe")
//                .build();
//    }
//
//    @Bean(name = "ticTacToeTransactionManager")
//    @Primary
//    public PlatformTransactionManager ticTacToeTransactionManager(
//            @Qualifier("ticTacToeEntityManagerFactory") EntityManagerFactory entityManagerFactory
//    ) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
