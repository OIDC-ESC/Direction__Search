package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // rest api 만들 때마다 여기에 추가
        http.authorizeRequests()
                .antMatchers("/api/drive").permitAll()
                .antMatchers("/api/driver").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
