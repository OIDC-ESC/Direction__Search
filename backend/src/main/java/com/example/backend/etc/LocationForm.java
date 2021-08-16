package com.example.backend.etc;

public class LocationForm { // (필요없는 경우 삭제)
    private String src_latitude; // 출발지 위도
    private String src_longitude; // 출발지 경도
    private String dst_latitude; // 도착지 위도
    private String dst_longitude; // 도착지 경도

    public String getSrc_latitude() { return src_latitude; }
    public void setSrc_latitude(String src_latitude) { this.src_latitude = src_latitude; }

    public String getSrc_longitude() { return src_longitude; }
    public void setSrc_longitude(String src_longitude) { this.src_longitude = src_longitude; }

    public String getDst_latitude() { return dst_latitude; }
    public void setDst_latitude(String dst_latitude) { this.dst_latitude = dst_latitude; }

    public String getDst_longitude() { return dst_longitude; }
    public void setDst_longitude(String dst_longitude) { this.dst_longitude = dst_longitude; }
}
