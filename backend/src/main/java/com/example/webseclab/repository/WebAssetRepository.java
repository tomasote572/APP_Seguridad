package com.example.webseclab.repository;

import com.example.webseclab.model.WebAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebAssetRepository extends JpaRepository<WebAsset, Long> {
}
