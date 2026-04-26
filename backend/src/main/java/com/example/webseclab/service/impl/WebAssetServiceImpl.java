package com.example.webseclab.service.impl;

import com.example.webseclab.model.WebAsset;
import com.example.webseclab.repository.WebAssetRepository;
import com.example.webseclab.service.WebAssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebAssetServiceImpl implements WebAssetService {
    private final WebAssetRepository repository;

    public WebAssetServiceImpl(WebAssetRepository repository) {
        this.repository = repository;
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
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
