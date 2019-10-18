package com.hecy.jdbctools.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author: hecy
 * @Date: 2019/10/18 16:31
 * @Version 1.0
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        String profile = env.getActiveProfiles()[0];
        // 生产环境添加用户用户名和密码
        if (!"prod".equals(profile)) {
            bean.addInitParameter("loginUsername", "admin");
            bean.addInitParameter("loginPassword", "admin123456");
        }
        return bean;
    }

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Bean
    public DataSource druidDataSource(@Value("${spring.datasource.driverClassName}") String driver, @Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String username, @Value("${spring.datasource.password}") String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);

        // 连接池
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(10);
        druidDataSource.setMaxActive(200);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(true);

        druidDataSource.setKeepAlive(true);

        druidDataSource.setPassword(password);
        druidDataSource.setConnectionProperties(connectionProperties);
        try {
            druidDataSource.setFilters("config, stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(@Qualifier("druidDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
