package com.example.webseclab.service;

import com.example.webseclab.model.Mitigation;

import java.util.List;

public interface MitigationService {
    List<Mitigation> findAll();
    Mitigation findById(Long id);
    Mitigation save(Mitigation mitigation);
    void deleteById(Long id);
}
