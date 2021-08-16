package com.example.backend.service.implement;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.backend.config.ApplicationConfig;
public class DirectionSearchServiceTest {
    public static void main(String[] args) throws IOException, ParseException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DirectionSearchServiceImpl directionSearchService = ac.getBean(DirectionSearchServiceImpl.class);
        // find route 테스트
        int auth = 1;
        double src_longitude = 127.12345;
        double src_latitude = 37.12345;
        double dst_longitude = 127.105399;
        double dst_latitude = 37.3595704;
        directionSearchService.findRoute(1, auth, src_longitude, src_latitude, dst_longitude, dst_latitude);
    }
}
