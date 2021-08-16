package com.example.backend.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.backend.config.ApplicationConfig;
import com.example.backend.dto.TrafficLight;

public class TrafficLightDaoTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        TrafficLightDao trafficLightDao = ac.getBean(TrafficLightDao.class);

        // 1. select all
        List<TrafficLight> list = trafficLightDao.selectAll();
        //System.out.println(list);

        // 2. select by location
        List<TrafficLight> trafficLight = trafficLightDao.getTrafficLight(37.3599017, 127.1044453);
        System.out.println(trafficLight);

        // 3. insert
        TrafficLight element = new TrafficLight();
        element.setLatitude(37.3599017);
        element.setLongitude(127.1044453);
        element.setTrafficColor(3);
        element.setTrafficOrder("녹색+황색+적색");
        element.setTrafficTime("20+3+52");

        int insertCount = trafficLightDao.insertTrafficInfo(element);
        System.out.println(insertCount + "개의 traffic 정보를 올렸습니다.");

    }
}
