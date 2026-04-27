package com.example.webseclab.config;

import com.example.webseclab.model.WebAsset;
import com.example.webseclab.model.AssetType;
import com.example.webseclab.model.VulnerabilityFinding;
import com.example.webseclab.model.Severity;
import com.example.webseclab.model.TrafficLog;
import com.example.webseclab.model.Mitigation;
import com.example.webseclab.repository.MitigationRepository;
import com.example.webseclab.repository.TrafficLogRepository;
import com.example.webseclab.repository.VulnerabilityFindingRepository;
import com.example.webseclab.repository.WebAssetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(WebAssetRepository assetRepository,
                               VulnerabilityFindingRepository findingRepository,
                               TrafficLogRepository trafficLogRepository,
                               MitigationRepository mitigationRepository) {
        return args -> {
            if (assetRepository.count() > 0) return;

            WebAsset dvwa = assetRepository.save(WebAsset.builder()
                    .name("DVWA Lab")
                    .type(AssetType.WEB)
                    .url("http://localhost/dvwa")
                    .technology("PHP, Apache, MySQL")
                    .description("Aplicación vulnerable para practicar OWASP Top 10 en laboratorio controlado.")
                    .build());

            VulnerabilityFinding sqli = findingRepository.save(VulnerabilityFinding.builder()
                    .asset(dvwa)
                    .title("SQL Injection en login")
                    .owaspCategory("A03:2021 - Injection")
                    .endpoint("/vulnerabilities/sqli/")
                    .method("POST")
                    .parameterName("id")
                    .payload("' OR '1'='1")
                    .evidence("Se obtuvieron registros no autorizados durante la prueba en entorno autorizado.")
                    .severity(Severity.HIGH)
                    .recommendation("Usar consultas parametrizadas y validación de entradas.")
                    .build());

            trafficLogRepository.save(TrafficLog.builder()
                    .protocolName("HTTP")
                    .sourceIp("192.168.56.10")
                    .destinationIp("192.168.56.20")
                    .observation("Se observan parámetros vulnerables y respuestas del servidor en texto claro.")
                    .sensitiveDataExposed(true)
                    .build());

            mitigationRepository.save(Mitigation.builder()
                    .finding(sqli)
                    .controlType("Control técnico")
                    .priorityLevel("Alta")
                    .status("Propuesta")
                    .actionPlan("Aplicar prepared statements, sanitización de entradas y HTTPS interno de laboratorio.")
                    .build());
        };
    }
}
