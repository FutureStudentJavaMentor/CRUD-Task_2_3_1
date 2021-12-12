package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "web")
public class DataSourceConfiguration {

    @Autowired
    private  Environment env;



    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setDataSource(dataSource());
        lc.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        lc.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        lc.setJpaProperties(getProperties());
        return lc;
    }

    public Properties getProperties() {
        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect",env.getRequiredProperty( "hibernate.dialect"));
        prop.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        prop.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        prop.setProperty("hibernate.connection.characterEncoding","utf8");
        prop.setProperty("hibernate.connection.CharSet","utf8");
        prop.setProperty("hibernate.connection.useUnicode","true");

        return prop;
    }



    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

}
