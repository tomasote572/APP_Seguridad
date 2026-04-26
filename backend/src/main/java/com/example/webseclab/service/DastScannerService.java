package com.example.webseclab.service;

import com.example.webseclab.model.Severity;
import com.example.webseclab.model.VulnerabilityFinding;
import com.example.webseclab.model.WebAsset;
import com.example.webseclab.model.Mitigation;
import com.example.webseclab.model.TrafficLog;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DastScannerService {

    private final VulnerabilityFindingService findingService;
    private final MitigationService mitigationService;
    private final TrafficLogService trafficLogService;

    public DastScannerService(VulnerabilityFindingService findingService, 
                              MitigationService mitigationService, 
                              TrafficLogService trafficLogService) {
        this.findingService = findingService;
        this.mitigationService = mitigationService;
        this.trafficLogService = trafficLogService;
    }

    public void scanAsset(WebAsset asset) {
        try {
            if (asset.getUrl() == null || asset.getUrl().isEmpty()) {
                return;
            }
            
            URL url = new URL(asset.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.connect();
            
            // Registro de tráfico automatizado
            trafficLogService.save(TrafficLog.builder()
                    .protocolName(url.getProtocol().toUpperCase())
                    .sourceIp("127.0.0.1 (DAST Scanner)")
                    .destinationIp(url.getHost())
                    .observation("Escaneo DAST automatizado en el puerto " + (url.getPort() == -1 ? url.getDefaultPort() : url.getPort()))
                    .sensitiveDataExposed(false)
                    .build());

            String serverHeader = con.getHeaderField("Server");
            if (serverHeader != null) {
                VulnerabilityFinding finding = findingService.save(VulnerabilityFinding.builder()
                        .asset(asset)
                        .title("Información del Servidor Expuesta")
                        .owaspCategory("A05:2021-Security Misconfiguration")
                        .endpoint(asset.getUrl())
                        .severity(Severity.LOW)
                        .evidence("Cabecera Server revelada: " + serverHeader)
                        .recommendation("Ocultar o enmascarar la cabecera Server para evitar fingerprinting.")
                        .build());
                        
                mitigationService.save(Mitigation.builder()
                        .finding(finding)
                        .controlType("Preventivo")
                        .priorityLevel("Baja")
                        .status("Pendiente")
                        .actionPlan("Configurar el servidor web para omitir la cabecera 'Server'.")
                        .build());
            }

            if (con.getHeaderField("X-Frame-Options") == null) {
                VulnerabilityFinding finding = findingService.save(VulnerabilityFinding.builder()
                        .asset(asset)
                        .title("Falta cabecera X-Frame-Options (Clickjacking)")
                        .owaspCategory("A05:2021-Security Misconfiguration")
                        .endpoint(asset.getUrl())
                        .severity(Severity.MEDIUM)
                        .evidence("La respuesta HTTP no incluye la cabecera X-Frame-Options.")
                        .recommendation("Configurar X-Frame-Options a DENY o SAMEORIGIN.")
                        .build());
                        
                mitigationService.save(Mitigation.builder()
                        .finding(finding)
                        .controlType("Preventivo")
                        .priorityLevel("Media")
                        .status("Pendiente")
                        .actionPlan("Añadir la cabecera X-Frame-Options: SAMEORIGIN en la configuración global.")
                        .build());
            }

            if (asset.getUrl().startsWith("https") && con.getHeaderField("Strict-Transport-Security") == null) {
                VulnerabilityFinding finding = findingService.save(VulnerabilityFinding.builder()
                        .asset(asset)
                        .title("Falta cabecera HSTS")
                        .owaspCategory("A05:2021-Security Misconfiguration")
                        .endpoint(asset.getUrl())
                        .severity(Severity.MEDIUM)
                        .evidence("Falta Strict-Transport-Security en conexión HTTPS.")
                        .recommendation("Implementar HSTS para forzar conexiones seguras HTTPS.")
                        .build());
                        
                mitigationService.save(Mitigation.builder()
                        .finding(finding)
                        .controlType("Preventivo")
                        .priorityLevel("Media")
                        .status("Pendiente")
                        .actionPlan("Habilitar Strict-Transport-Security con max-age=31536000.")
                        .build());
            }

        } catch (Exception e) {
            findingService.save(VulnerabilityFinding.builder()
                    .asset(asset)
                    .title("Fallo al escanear el objetivo")
                    .owaspCategory("A05:2021-Security Misconfiguration")
                    .endpoint(asset.getUrl())
                    .severity(Severity.LOW)
                    .evidence("Error de conexión: " + e.getMessage())
                    .recommendation("Verificar que la URL sea accesible.")
                    .build());
        }
    }
}
