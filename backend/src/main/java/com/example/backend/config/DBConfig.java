package com.example.backend.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration // DB 설정 정보
@EnableTransactionManagement
public class DBConfig implements TransactionManagementConfigurer{
    // for DB 연결
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/oidc?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
    private String username = "esc";
    private String password = "cyber";

    @Bean
    public DataSource dataSource() { // DB 이용
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManger();
    }

    @Bean
    public PlatformTransactionManager transactionManger() {
        return new DataSourceTransactionManager(dataSource());
    }
}
