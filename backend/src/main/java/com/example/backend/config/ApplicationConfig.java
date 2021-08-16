package com.example.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // 설정 정보
@ComponentScan(basePackages = {"com.example.backend.dao", "com.example.backend.service"})
@Import({DBConfig.class, WebSecurityConfig.class, WebConfig.class, CorsFilter.class})
public class ApplicationConfig {
}
