package com.example.webseclab.controller;

import com.example.webseclab.model.TrafficLog;
import com.example.webseclab.service.TrafficLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/traffic")
public class TrafficLogController {
    private final TrafficLogService service;

    public TrafficLogController(TrafficLogService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("logs", service.findAll());
        model.addAttribute("log", new TrafficLog());
        return "traffic";
    }

    @PostMapping
    public String save(@ModelAttribute TrafficLog log) {
        service.save(log);
        return "redirect:/traffic";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/traffic";
    }
}
