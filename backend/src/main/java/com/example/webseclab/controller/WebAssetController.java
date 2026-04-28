package com.example.webseclab.controller;

import com.example.webseclab.model.AssetType;
import com.example.webseclab.model.WebAsset;
import com.example.webseclab.service.WebAssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assets")
public class WebAssetController {
    private final WebAssetService service;
    private final com.example.webseclab.service.DastScannerService scannerService;

    public WebAssetController(WebAssetService service, com.example.webseclab.service.DastScannerService scannerService) {
        this.service = service;
        this.scannerService = scannerService;
    }

    @GetMapping("/scan/{id}")
    public String scan(@PathVariable Long id) {
        WebAsset asset = service.findById(id);
        if (asset != null) {
            scannerService.scanAsset(asset);
        }
        return "redirect:/findings";
    }

    @PostMapping("/quick-scan")
    public String quickScan(@RequestParam String url) {
        WebAsset asset = new WebAsset();
        asset.setName("DAST Rápido");
        asset.setType(AssetType.WEB);
        asset.setUrl(url);
        
        // Intentar detectar tecnología
        String tech = "-";
        try {
            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            String server = connection.getHeaderField("Server");
            String xPoweredBy = connection.getHeaderField("X-Powered-By");
            
            if (server != null && xPoweredBy != null) {
                tech = server + ", " + xPoweredBy;
            } else if (server != null) {
                tech = server;
            } else if (xPoweredBy != null) {
                tech = xPoweredBy;
            } else {
                tech = "Oculta / No detectada";
            }
        } catch (Exception e) {
            tech = "Error de conexión";
        }
        
        asset.setTechnology(tech);
        asset.setDescription("Escaneo automatizado rápido.");
        WebAsset saved = service.save(asset);
        scannerService.scanAsset(saved);
        return "redirect:/findings";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assets", service.findAll());
        model.addAttribute("asset", new WebAsset());
        model.addAttribute("types", AssetType.values());
        return "assets";
    }

    @PostMapping
    public String save(@ModelAttribute WebAsset asset) {
        service.save(asset);
        return "redirect:/assets";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/assets";
    }
}
