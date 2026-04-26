package com.example.webseclab.service;

import com.example.webseclab.model.WebAsset;

import java.util.List;

public interface WebAssetService {
    List<WebAsset> findAll();
    WebAsset findById(Long id);
    WebAsset save(WebAsset asset);
    void deleteById(Long id);
}
