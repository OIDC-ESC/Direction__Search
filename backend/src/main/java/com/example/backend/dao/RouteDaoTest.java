package com.example.backend.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.backend.config.ApplicationConfig;
import com.example.backend.dto.Route;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RouteDaoTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        RouteDao routeDao = ac.getBean(RouteDao.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1. insert
        Route route = new Route();
        String now = format.format(new Date());
        route.setLatitude(37.12345);
        route.setLongitude(127.12345);
        route.setEmergencyCarId(1);
        route.setNodeId(0);
        route.setTime(now);
        int insertCount = routeDao.insertRoute(route);
        System.out.println(insertCount + "개의 경로를 올렸습니다.");

        // 2. delete by time
        String currentTime = format.format(new Date());
        int deleteCount = routeDao.deleteRoute(currentTime);
        System.out.println(deleteCount + "개의 경로를 삭제했습니다.");

        // 3. count
        int count = routeDao.isEmpty();
        System.out.println(count + "개의 레코드가 존재합니다.");

        // 4. sort by time
        Route route2 = routeDao.findClosestNode();
        System.out.println(route2);
    }
}
