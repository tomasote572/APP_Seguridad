package com.example.webseclab.service.impl;

import com.example.webseclab.model.Mitigation;
import com.example.webseclab.repository.MitigationRepository;
import com.example.webseclab.service.MitigationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MitigationServiceImpl implements MitigationService {
    private final MitigationRepository repository;

    public MitigationServiceImpl(MitigationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Mitigation> findAll() {
        return repository.findAll();
    }

    @Override
    public Mitigation findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Mitigación no encontrada"));
    }

    @Override
    public Mitigation save(Mitigation mitigation) {
        return repository.save(mitigation);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
