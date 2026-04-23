package com.example.webseclab.service;

import com.example.webseclab.model.TrafficLog;

import java.util.List;

public interface TrafficLogService {
    List<TrafficLog> findAll();
    TrafficLog findById(Long id);
    TrafficLog save(TrafficLog log);
    void deleteById(Long id);
}
