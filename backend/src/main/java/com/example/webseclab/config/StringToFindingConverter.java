package com.example.webseclab.config;

import com.example.webseclab.model.VulnerabilityFinding;
import com.example.webseclab.service.VulnerabilityFindingService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFindingConverter implements Converter<String, VulnerabilityFinding> {
    private final VulnerabilityFindingService service;

    public StringToFindingConverter(VulnerabilityFindingService service) {
        this.service = service;
    }

    @Override
    public VulnerabilityFinding convert(String source) {
        if (source == null || source.isBlank()) return null;
        return service.findById(Long.parseLong(source));
    }
}
