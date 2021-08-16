package com.example.backend.etc;

public class Location { // (필요없는 경우 삭제)
    private GeoRef src = new GeoRef(); // 출발지 좌표
    private GeoRef dst = new GeoRef(); // 도착지 좌표

    public GeoRef getSrc() { return src; }

    public void setSrc(String src_latitude, String src_longitude) {
        // String -> Double
        double latitude = Double.parseDouble(src_latitude);
        double longitude = Double.parseDouble(src_longitude);

        this.src.setLatitude(latitude);
        this.src.setLongitude(longitude);
    }

    public GeoRef getDst() { return dst; }

    public void setDst(String dst_latitude, String dst_longitude) {
        // String -> Double
        double latitude = Double.parseDouble(dst_latitude);
        double longitude = Double.parseDouble(dst_longitude);

        this.dst.setLatitude(latitude);
        this.dst.setLongitude(longitude);
    }
}
