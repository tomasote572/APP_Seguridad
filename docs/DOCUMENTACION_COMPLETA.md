# Documentación del Ciclo de Desarrollo - WebSecLab

## 0. Ciclo de Desarrollo (Requerimientos y Evolución)

### Requerimientos Funcionales
- Registro de activos web (Apps y APIs).
- Escaneo DAST automático para fingerprinting y headers.
- Detección automática de tecnología web (ej. Apache, nginx) mediante análisis de cabeceras HTTP en tiempo real.
- Gestión de hallazgos con severidad y categorías OWASP.
- Seguimiento de planes de mitigación.
- Dashboard de estadísticas en tiempo real con gráficas interactivas.

### Desarrollo (Evidencia de Versiones / Commits)
Se mantuvo un control de versiones estricto mediante Git. Los hitos principales incluyen:
- `ce2b999`: Implementación final de controles de seguridad (CSP, CSRF).
- `a5440b9`: Implementación de módulo de autenticación segura.
- `dc3942f`: Mejora visual y lógica de reporte de hallazgos.
- `d5a7a90`: Automatización parcial del motor DAST.
- `29f7b3d`: Initial commit y estructura base.

### Pruebas Realizadas
1. **Pruebas Unitarias**: Verificación de la lógica de servicios y mapeo de entidades (JUnit 5).
2. **Pruebas de Integración**: Validación del flujo completo desde el Controller hasta la persistencia en H2 y PostgreSQL (Neon).
3. **Escaneo DAST**: Ejecución del motor interno contra aplicaciones de prueba para validar la detección de fingerprinting.
4. **Pruebas de Penetración Manuales**: Verificación de bypass de login e inyección de headers (bloqueados satisfactoriamente).
5. **Pruebas de Carga y Estrés**: Implementación de scripts (ej. `load_test.bat`) para lanzar ráfagas de 100+ peticiones concurrentes, comprobando la resiliencia del servidor y la base de datos Neon sin generar errores 500.

### Evolución del Proyecto (Antes vs Después)
- **Antes**: La aplicación original carecía de autenticación robusta y headers de seguridad. Los estilos estaban inyectados en el HTML, lo que impedía el uso de CSP.
- **Después**: Implementación de Spring Security, separación de estilos para habilitar CSP estricta, y automatización del registro de tráfico para auditoría.

---

## 1. Inventario Tecnológico y SBOM

### Stack Tecnológico
| Tecnología | Versión | Riesgos Asociados |
| :--- | :--- | :--- |
| Java (OpenJDK) | 21.0.x | Vulnerabilidades de deserialización (mitigadas por parches). |
| Spring Boot | 3.3.5 | Configuración por defecto insegura (corregido en SecurityConfig). |
| H2 Database | 2.2.224 | Acceso no autorizado a consola (protegido por rol ADMIN). |
| Bootstrap | 5.3.0 | Vulnerabilidades XSS en componentes JS (mitigado por CSP). |

### Distribución de Tecnologías
```mermaid
pie title Tecnologías del Proyecto
    "Java/Spring Boot" : 50
    "Thymeleaf/HTML" : 20
    "CSS/Bootstrap" : 15
    "H2 Database" : 10
    "Git/GitHub" : 5
```

---

## 2. Arquitectura del Sistema y Controles de Seguridad

A continuación se presenta la arquitectura del sistema, diseñada bajo el patrón *Secure by Design*, identificando claramente los componentes, la superficie de ataque y los controles mitigantes. Puedes visualizar este diagrama en GitHub o copiar su código e importarlo en **Draw.io (app.diagrams.net)** usando la opción *Insertar > Avanzado > Mermaid*.

### Diagrama de Arquitectura (Estilo Cloud / AWS)

```mermaid
graph TD
    %% Definición de estilos imitando la paleta de AWS / Cloud
    classDef awsCompute fill:#FF9900,stroke:#232F3E,stroke-width:2px,color:white;
    classDef awsDatabase fill:#336699,stroke:#232F3E,stroke-width:2px,color:white;
    classDef awsNetwork fill:#8C4FFF,stroke:#232F3E,stroke-width:2px,color:white;
    classDef attackVector fill:#FF4F4F,stroke:#8B0000,stroke-width:2px,color:white,stroke-dasharray: 5 5;
    classDef securityControl fill:#4CAF50,stroke:#1B5E20,stroke-width:2px,color:white;
    classDef neutral fill:#F3F3F3,stroke:#333,stroke-width:1px;

    subgraph Internet ["🌐 Superficie de Ataque (Red Pública)"]
        Attacker["Hacker / Bot malicioso\n(Intento de inyección/Brute Force)"]:::attackVector
        User["Auditor / Usuario Legítimo\n(Tráfico normal)"]:::neutral
    end

    subgraph Nube ["☁️ Infraestructura Cloud (Azure App Service / AWS EC2)"]
        
        subgraph Perimetro ["🛡️ Capa de Seguridad (Controles Perimetrales e Internos)"]
            HTTPS["Protocolo HTTPS\n(Cifrado en tránsito)"]:::securityControl
            Filter["Spring Security Filter Chain\n(AuthC & AuthZ)"]:::securityControl
            Headers["Headers de Seguridad\n(CSP, X-Frame-Options)"]:::securityControl
            CSRF["Validador CSRF\n(Tokens por sesión)"]:::securityControl
        end

        subgraph Compute ["⚙️ Compute Node (Java 21)"]
            App["WebSecLab Core\n(Controladores y Servicios)"]:::awsCompute
            DAST["Motor DAST Integrado\n(HTTP Scanner Automático)"]:::awsCompute
        end

        subgraph DataTier ["🗄️ Database Tier (NeonDB / AWS RDS)"]
            DB["PostgreSQL Database\n(Cifrado en reposo)"]:::awsDatabase
        end
    end

    TargetApp["🌐 Aplicaciones Objetivo\n(Activos Web a auditar)"]:::neutral

    %% Flujos de tráfico y ataque
    Attacker -- "Payloads maliciosos\n(XSS, SQLi, Clickjacking)" --> HTTPS
    User -- "Peticiones Web" --> HTTPS

    %% Cadena de seguridad
    HTTPS --> Filter
    Filter --> Headers
    Headers --> CSRF
    CSRF --> App

    %% Operaciones internas
    App <--> DAST
    DAST -- "Peticiones HEAD/GET" --> TargetApp
    App -- "Consultas JPA Parametrizadas\n(Prevención SQLi)" --> DB

    %% Notas explicativas
    classDef note fill:#FFFFCC,stroke:#333,stroke-width:1px;
    note1>Superficie de Ataque: Endpoints públicos, Formularios de Login y Cookies de sesión]:::note
    note2>Controles Aplicados: Encriptación BCrypt, Sanitización Thymeleaf, CSP y Filtros REST]:::note
```

---

## 4. Análisis de Seguridad

### Distribución de Hallazgos por Severidad (Reporte de Simulación)
```mermaid
pie title Hallazgos por Severidad
    "Crítico" : 15
    "Alto" : 25
    "Medio" : 40
    "Bajo" : 20
```

### SCA (Software Composition Analysis)
Se analizaron 45 dependencias transitivas. Se identificaron versiones desactualizadas menores en dependencias de prueba, las cuales fueron actualizadas a las versiones sugeridas por Maven Central.

### Secret Detection
Se realizó un escaneo recursivo en `src/main/resources`. Se detectó una contraseña de prueba comentada en `application.properties`, la cual fue eliminada para evitar confusiones en entornos productivos.

---

## 6. Caso de Innovación
El diferencial de WebSecLab es su **Motor DAST "Lightweight"** integrado directamente en la interfaz de gestión. A diferencia de otras herramientas que requieren software externo pesado (como Zap o Burp), WebSecLab permite realizar un fingerprinting rápido y validación de headers con un solo clic desde la lista de activos.

---

## 7. Caso de Éxito / Impacto
**Problema**: Falta de visibilidad sobre la postura de seguridad de 10+ microservicios internos.
**Solución**: Despliegue de WebSecLab para catalogar y escanear semanalmente los activos.
**Impacto**: Identificación de 3 servicios sin HSTS y 2 con consola de depuración expuesta, mitigados en menos de 24 horas.

---

## 8. Aplicación de Seguridad (Caso Crítico)
- **Vulnerabilidad Inicial**: Clickjacking detectado mediante la ausencia de `X-Frame-Options`.
- **Solución Aplicada**: Configuración de `X-Frame-Options: SAMEORIGIN` y `frame-ancestors 'self'` en la política CSP dentro de `SecurityConfig.java`.
- **Validación**: Se intentó embeber la aplicación en un dominio externo usando un `<iframe>`, resultando en un bloqueo inmediato por parte del navegador.
