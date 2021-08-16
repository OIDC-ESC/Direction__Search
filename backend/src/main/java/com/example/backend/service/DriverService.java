package com.example.backend.service;

import com.example.backend.etc.DriverForm;

public interface DriverService {
    // 예상 시각과 가장 비슷한 노드 찾아서 프론트엔드에 전해주기
    public DriverForm findClosestNode();
}
