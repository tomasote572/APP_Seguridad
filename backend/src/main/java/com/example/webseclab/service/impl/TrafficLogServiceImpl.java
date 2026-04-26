package com.example.webseclab.service.impl;

import com.example.webseclab.model.TrafficLog;
import com.example.webseclab.repository.TrafficLogRepository;
import com.example.webseclab.service.TrafficLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficLogServiceImpl implements TrafficLogService {
    private final TrafficLogRepository repository;

    public TrafficLogServiceImpl(TrafficLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TrafficLog> findAll() {
        return repository.findAll();
    }

    @Override
    public TrafficLog findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Registro de tráfico no encontrado"));
    }

    @Override
    public TrafficLog save(TrafficLog log) {
        return repository.save(log);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
