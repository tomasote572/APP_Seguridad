package com.example.webseclab.controller;

import com.example.webseclab.model.Mitigation;
import com.example.webseclab.service.MitigationService;
import com.example.webseclab.service.VulnerabilityFindingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mitigations")
public class MitigationController {
    private final MitigationService service;
    private final VulnerabilityFindingService findingService;

    public MitigationController(MitigationService service, VulnerabilityFindingService findingService) {
        this.service = service;
        this.findingService = findingService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("mitigations", service.findAll());
        model.addAttribute("mitigation", new Mitigation());
        model.addAttribute("findings", findingService.findAll());
        return "mitigations";
    }

    @PostMapping
    public String save(@ModelAttribute Mitigation mitigation) {
        service.save(mitigation);
        return "redirect:/mitigations";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/mitigations";
    }
}
