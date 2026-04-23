package com.example.webseclab.repository;

import com.example.webseclab.model.TrafficLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficLogRepository extends JpaRepository<TrafficLog, Long> {
}
