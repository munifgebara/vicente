package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.vicente.application.VicRepositoryImpl;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages =         {"br.com.munif.framework.test.vicente.application"})
@EnableJpaRepositories(basePackages = {"br.com.munif.framework.test.vicente.application"}, repositoryBaseClass = VicRepositoryImpl.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
public class MySQLSpringConfig {

    public MySQLSpringConfig() {
        System.out.println("---->" + this.getClass().getCanonicalName());
    }

    @Bean
    public static DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("url", "jdbc:mysql://localhost:3306/victeste?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=utf8&createDatabaseIfNotExist=true");
        config.addDataSourceProperty("user", "root");
        config.addDataSourceProperty("password", "senha");
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000L);
        return new HikariDataSource(config);
    }
    
        @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        Properties properties = new Properties();
        properties.put("eclipselink.weaving", "false");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.EJB3NamingStrategy");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.connection.charSet", "UTF-8");
        properties.put("hibernate.connection.characterEncoding", "UTF-8");
        properties.put("hibernate.connection.useUnicode", "true");
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("br.com.munif.framework.vicente.domain", "br.com.munif.framework.test.vicente.application.model");

        factory.setDataSource(dataSource);
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}