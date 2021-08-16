package com.example.backend.service.implement;

import com.example.backend.dao.RouteDao;
import com.example.backend.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmergencyServiceImpl implements EmergencyService{

    @Autowired
    RouteDao routeDao;

    @Override
    public void deletePassedNode() { // 예상 시각 지나간 경우 노드 삭제
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(new Date());

        int deleteCount = routeDao.deleteRoute(now);
        System.out.println(deleteCount + "개의 노드를 삭제했습니다.");
    }
}
