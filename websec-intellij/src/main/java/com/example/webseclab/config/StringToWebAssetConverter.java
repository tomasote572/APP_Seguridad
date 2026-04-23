package com.example.webseclab.config;

import com.example.webseclab.model.WebAsset;
import com.example.webseclab.service.WebAssetService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToWebAssetConverter implements Converter<String, WebAsset> {
    private final WebAssetService service;

    public StringToWebAssetConverter(WebAssetService service) {
        this.service = service;
    }

    @Override
    public WebAsset convert(String source) {
        if (source == null || source.isBlank()) return null;
        return service.findById(Long.parseLong(source));
    }
}
