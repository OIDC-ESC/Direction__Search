package com.example.backend.dao;

public class TrafficLightSqls {
    public static final String SELECT_BY_LOCATION = "SELECT traffic_order, traffic_time FROM traffic_light WHERE latitude = :latitude and longitude = :longitude";
    public static final String SELECT_ALL = "SELECT * FROM traffic_light";
}
