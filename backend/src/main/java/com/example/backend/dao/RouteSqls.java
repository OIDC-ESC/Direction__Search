package com.example.backend.dao;

public class RouteSqls {
    // (일반 차량 서비스) 데이터베이스 비어있는지 확인
    public static final String IS_DB_EMPTY = "SELECT COUNT(*) FROM route";
    // (일반 차량 서비스) 현재 시각과 가까운 예상 시각 순으로 정렬
    public static final String SORT_BY_TIME= "SELECT * FROM route ORDER BY ABS(NOW() - time) LIMIT 1";
    // (응급 차량 서비스) 해당 응급 차량이 지나가는 노드 중 예상 시각이 지나간 노드 삭제
    public static final String DELETE_BY_TIME = "DELETE FROM route WHERE time <= :time";
}
