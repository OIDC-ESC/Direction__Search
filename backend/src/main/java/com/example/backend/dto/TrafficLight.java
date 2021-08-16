package com.example.backend.dto;

public class TrafficLight {
    private Double latitude;
    private Double longitude;
    private int trafficColor;
    private String trafficOrder;
    private String trafficTime;

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
    public int getTrafficColor() {
        return trafficColor;
    }
    public void setTrafficColor(int trafficColor) {
        this.trafficColor = trafficColor;
    }
    public String getTrafficOrder() {
        return trafficOrder;
    }
    public void setTrafficOrder(String trafficOrder) {
        this.trafficOrder = trafficOrder;
    }
    public String getTrafficTime() {
        return trafficTime;
    }
    public void setTrafficTime(String trafficTime) {
        this.trafficTime = trafficTime;
    }
    @Override
    public String toString() {
        if(latitude != null)
            return "TrafficLight [latitude=" + latitude + ", longitude=" + longitude + ", trafficColor=" + trafficColor
                    + ", trafficOrder=" + trafficOrder + ", trafficTime=" + trafficTime + "]";
        else return "TrafficLight [trafficOrder=" + trafficOrder + ", trafficTime=" + trafficTime + "]";
    }
}
