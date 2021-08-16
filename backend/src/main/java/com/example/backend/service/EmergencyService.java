package com.example.backend.service;

public interface EmergencyService{
    // 예상 시각 지나간 경우 노드 삭제
    public void deletePassedNode();
}