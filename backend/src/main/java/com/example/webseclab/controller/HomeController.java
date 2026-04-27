package com.example.webseclab.controller;

import com.example.webseclab.service.MitigationService;
import com.example.webseclab.service.TrafficLogService;
import com.example.webseclab.service.VulnerabilityFindingService;
import com.example.webseclab.service.WebAssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final WebAssetService assetService;
    private final VulnerabilityFindingService findingService;
    private final TrafficLogService trafficLogService;
    private final MitigationService mitigationService;

    public HomeController(WebAssetService assetService,
                          VulnerabilityFindingService findingService,
                          TrafficLogService trafficLogService,
                          MitigationService mitigationService) {
        this.assetService = assetService;
        this.findingService = findingService;
        this.trafficLogService = trafficLogService;
        this.mitigationService = mitigationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("assets", assetService.findAll().size());
        model.addAttribute("findings", findingService.findAll().size());
        model.addAttribute("traffic", trafficLogService.findAll().size());
        model.addAttribute("mitigations", mitigationService.findAll().size());
        model.addAttribute("latestFindings", findingService.findAll());
        
        // Datos para gráficas
        model.addAttribute("criticalCount", findingService.countBySeverity("CRITICAL"));
        model.addAttribute("highCount", findingService.countBySeverity("HIGH"));
        model.addAttribute("mediumCount", findingService.countBySeverity("MEDIUM"));
        model.addAttribute("lowCount", findingService.countBySeverity("LOW"));
        
        return "index";
    }
}
