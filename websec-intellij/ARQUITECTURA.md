# Arquitectura del proyecto

## Patrón usado
Arquitectura en capas con patrón MVC + Service + Repository.

## Estructura
```text
src/main/java/com/example/webseclab/
├── WebSecLabApplication.java
├── config/
│   └── DataLoader.java
├── controller/
│   ├── HomeController.java
│   ├── WebAssetController.java
│   ├── VulnerabilityFindingController.java
│   ├── TrafficLogController.java
│   └── MitigationController.java
├── model/
│   ├── AssetType.java
│   ├── Severity.java
│   ├── WebAsset.java
│   ├── VulnerabilityFinding.java
│   ├── TrafficLog.java
│   └── Mitigation.java
├── repository/
│   ├── WebAssetRepository.java
│   ├── VulnerabilityFindingRepository.java
│   ├── TrafficLogRepository.java
│   └── MitigationRepository.java
├── service/
│   ├── WebAssetService.java
│   ├── VulnerabilityFindingService.java
│   ├── TrafficLogService.java
│   ├── MitigationService.java
│   └── impl/
│       ├── WebAssetServiceImpl.java
│       ├── VulnerabilityFindingServiceImpl.java
│       ├── TrafficLogServiceImpl.java
│       └── MitigationServiceImpl.java
```

## Flujo
1. El usuario interactúa con una vista Thymeleaf.
2. El `controller` recibe el request.
3. El `service` aplica la lógica.
4. El `repository` consulta o persiste en H2.
5. La respuesta vuelve a la vista.

## Relación con el proyecto
El documento pide reconocimiento, explotación documentada, análisis de tráfico, mitigación y reporte dentro de un entorno ético y controlado. Esta app soporta precisamente el registro de esos componentes. fileciteturn2file1L5-L14
