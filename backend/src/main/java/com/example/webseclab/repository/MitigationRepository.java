package com.example.webseclab.repository;

import com.example.webseclab.model.Mitigation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MitigationRepository extends JpaRepository<Mitigation, Long> {
}
