package com.example.webseclab.service.impl;

import com.example.webseclab.model.WebAsset;
import com.example.webseclab.repository.WebAssetRepository;
import com.example.webseclab.repository.VulnerabilityFindingRepository;
import com.example.webseclab.service.WebAssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebAssetServiceImpl implements WebAssetService {
    private final WebAssetRepository repository;
    private final VulnerabilityFindingRepository findingRepository;

    public WebAssetServiceImpl(WebAssetRepository repository, VulnerabilityFindingRepository findingRepository) {
        this.repository = repository;
        this.findingRepository = findingRepository;
    }

    @Override
    public List<WebAsset> findAll() {
        return repository.findAll();
    }

    @Override
    public WebAsset findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Activo no encontrado"));
    }

    @Override
    public WebAsset save(WebAsset asset) {
        return repository.save(asset);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Primero eliminar todos los hallazgos asociados al activo
        var findings = findingRepository.findAll().stream()
                .filter(f -> f.getAsset() != null && f.getAsset().getId().equals(id))
                .toList();
        findings.forEach(f -> {
            // Eliminar mitigaciones del hallazgo
            if (f.getMitigations() != null) {
                f.getMitigations().clear();
            }
            findingRepository.delete(f);
        });
        // Finalmente eliminar el activo
        repository.deleteById(id);
    }
}
