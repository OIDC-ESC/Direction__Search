package com.example.backend.service.implement;

import com.example.backend.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DriverServiceTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DriverServiceImpl driverService = ac.getBean(DriverServiceImpl.class);

        // findClosetNode
        driverService.findClosestNode();
    }
}
