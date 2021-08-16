package com.example.backend.dto;

public class Route {
    private Double latitude;
    private Double longitude;
    private int emergencyCarId;
    private int nodeId;
    private String time;

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public int getEmergencyCarId() {
        return emergencyCarId;
    }
    public void setEmergencyCarId(int emergencyCarId) {
        this.emergencyCarId = emergencyCarId;
    }
    public int getNodeId() { return nodeId; }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    public String getTime() { return time; }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Route{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", emergencyCarId=" + emergencyCarId +
                ", nodeId=" + nodeId +
                ", time=" + time +
                '}';
    }
}
