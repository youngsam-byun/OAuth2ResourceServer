package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by byun.ys on 4/12/2017.
 */

@Configuration
@PropertySource("classpath:db/db.properties")
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${jdbc.driverClassName}")
    private static String driverClassName;

    @Value("${jdbc.url}")
    private static String url;

    @Value("${jdbc.username}")
    private static String username;

    @Value("${jdbc.password}")
    private static String password;

    @Value("${jdbc.initialSize}")
    private static int intialSize;

    @Value("${jdbc.maxTotal}")
    private static int maxTotal;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourceConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(intialSize);
        dataSource.setMaxTotal(maxTotal);

        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
