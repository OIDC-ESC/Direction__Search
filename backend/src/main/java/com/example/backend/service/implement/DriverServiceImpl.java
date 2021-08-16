package com.example.backend.service.implement;

import com.example.backend.dao.RouteDao;
import com.example.backend.dto.Route;
import com.example.backend.etc.DriverForm;
import com.example.backend.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private RouteDao routeDao;

    @Override
    public DriverForm findClosestNode() { // 예상 시각과 가장 가까운 노드 정보 반환
        Route node = routeDao.findClosestNode();
        DriverForm form = new DriverForm();
        form.setSuccess(true);
        form.setLatitude(node.getLatitude());
        form.setLongitude(node.getLongitude());
        form.setTime(node.getTime());
        return form;
    }
}
