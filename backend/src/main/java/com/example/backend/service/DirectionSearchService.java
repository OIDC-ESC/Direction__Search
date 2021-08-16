package com.example.backend.service;

import java.io.IOException;

import com.example.backend.etc.RouteForm;
import org.json.simple.parser.ParseException;

import com.example.backend.etc.Coordinate;

public interface DirectionSearchService {
    // 응급/일반 차량에게 최적의 경로 제공 (응급 차량의 경로는 DB의 route 테이블에 저장)
    public double cal_ang(Coordinate a, Coordinate b, Coordinate c);
    public long traffic_value (int i, int j, double path[][][], long time_plus, double location[], double goal[]);
    public RouteForm findRoute(int emergencyCarId, int auth, double src_longitude, double src_latitude, double dst_longitude, double dst_latitude) throws IOException, ParseException;
}
