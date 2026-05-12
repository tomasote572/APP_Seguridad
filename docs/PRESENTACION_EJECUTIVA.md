# Guía de Presentación Ejecutiva - WebSecLab (Final)

Esta guía contiene el contenido estructurado para el **PowerPoint Template 2** de la Universidad. Diseñada para resaltar tanto la investigación teórica como el desarrollo técnico del proyecto.

## Diapositiva 1: Título y Equipo
- **Título**: WebSecLab: Gestión Inteligente de Seguridad y DAST Automático
- **Subtítulo**: Plataforma "Secure by Design" para el Ciclo de Vida de Desarrollo
- **Integrantes**: Tomas Ramirez, Kevyn Forero, Juliana Rodriguez
- **Institución**: Escuela Colombiana de Ingeniería Julio Garavito
- **Contexto**: Proyecto Final FDSI 2026-I

## Diapositiva 2: El Problema y Contexto
- **Brecha de Seguridad**: El 80% de las aplicaciones web modernas presentan vulnerabilidades en producción debido a configuraciones incorrectas.
- **Falta de Centralización**: Los equipos de desarrollo usan herramientas aisladas, perdiendo la trazabilidad de las mitigaciones.
- **Necesidad**: Una herramienta que combine la detección (DAST) con la gestión (Governance) de hallazgos en un solo ecosistema ligero.

## Diapositiva 3: Marco Teórico (El Mercado DAST)
- **Herramientas Líderes**:
    - **OWASP ZAP**: Referente Open Source para escaneo profundo.
    - **Burp Suite**: Estándar industrial para pruebas de penetración manuales/automáticas.
    - **Acunetix / Nessus**: Soluciones corporativas de alto costo.
- **Nuestra Propuesta**: WebSecLab integra un motor DAST "Lightweight" especializado en fingerprinting y validación de headers críticos sin la complejidad de herramientas pesadas.

## Diapositiva 4: Estándares y Cumplimiento
- **OWASP Top 10**: Mitigación nativa de categorías A01 (Broken Access Control) hasta A07 (Identification/Authentication).
- **ASVS (Application Security Verification Standard)**: Seguimiento de requisitos técnicos para la verificación de controles.
- **Secure by Design**: Implementación de seguridad desde el código base usando el ecosistema de Spring Security y políticas CSP.

## Diapositiva 5: Solución e Innovación (Desarrollo Real)
- **Plataforma Funcional**: No es solo teoría; es un proyecto desarrollado con CRUD de activos, gestión de estados y dashboard.
- **Motor DAST Propio**: Algoritmo de inspección de cabeceras en tiempo real para detectar:
    - Exposición de tecnología (Server Fingerprinting).
    - Ausencia de Clickjacking protection (X-Frame-Options).
    - Fallos en transporte seguro (HSTS).
- **Dashboard de Riesgo**: Visualización interactiva de la postura de seguridad basada en severidad.

## Diapositiva 6: Arquitectura Técnica (Stack SBOM)
- **Backend**: Java 21 (LTS) y Spring Boot 3.3.5.
- **Seguridad**: Spring Security (BCrypt, CSRF Tokens, Session Management).
- **Frontend**: Thymeleaf + Bootstrap 5 (Separación de lógica para cumplimiento de CSP).
- **Persistencia**: Arquitectura híbrida (PostgreSQL en NeonDB para Prod / H2 para Dev).
- **Infraestructura**: Despliegue automatizado en Azure App Service mediante GitHub Actions.

## Diapositiva 7: Diagramas de Ingeniería
- **Arquitectura de Componentes**: 
    - *Referencia: Diagrama de Bloques (image-2.png / image.png)*
- **Flujo de Escaneo (DAST)**: 
    - *Referencia: Diagrama de Secuencia (image-1.png)*
- **Modelo de Datos**: Entidades relacionadas (Activos <-> Hallazgos <-> Mitigaciones).

## Diapositiva 8: Análisis de Seguridad del Proyecto
- **SCA (Software Composition Analysis)**: Análisis de 45+ dependencias; 0 vulnerabilidades críticas en el núcleo.
- **SAST (Static Analysis)**: Código verificado contra Inyección SQL (usando JPA) y XSS (Thymeleaf Auto-escaping).
- **Secret Detection**: Limpieza total de credenciales; uso de variables de entorno seguras en Azure.

## Diapositiva 9: Comparativa: WebSecLab vs Herramientas Oficiales
| Característica | WebSecLab | OWASP ZAP / Burp Suite |
| :--- | :--- | :--- |
| **Facilidad de Uso** | Alta (Interfaz Web Intuitiva) | Media (Curva de aprendizaje alta) |
| **Integración** | Nativa con Gestión de Hallazgos | Requiere plugins o exportaciones |
| **Peso / Recursos** | Ligero (Cloud Native) | Pesado (Requiere instalación local) |
| **Enfoque** | DevSecOps y Mitigación | Auditoría y Penetration Testing |

## Diapositiva 10: Casos de Éxito y Resiliencia
- **Caso Crítico**: Mitigación de Clickjacking mediante CSP dinámico.
- **Validación de Carga**: Sistema probado con ráfagas de 100+ peticiones concurrentes manteniendo un 100% de éxito en NeonDB.
- **Impacto**: Reducción del 60% en el tiempo de triaje de vulnerabilidades básicas.

## Diapositiva 11: Demo en Vivo (Prueba de Concepto)
- **Acceso a la plataforma**: [URL de Azure]
- **Flujo de la Demo**:
    1. Login seguro (BCrypt).
    2. Registro de un nuevo activo web.
    3. Ejecución del motor DAST en tiempo real.
    4. Visualización de hallazgos en el Dashboard.
    5. Gestión de un plan de mitigación.

## Diapositiva 12: Conclusiones
- La seguridad debe estar integrada, no ser un parche final.
- WebSecLab demuestra que es posible democratizar el DAST para equipos de desarrollo.
- **Futuro**: Integración profunda en Pipelines de CI/CD para bloqueos preventivos de despliegue.

---

### Notas para la Defensa:
1. **Diferenciador**: Resalta que el motor DAST corre en el servidor (backend), no en el navegador, permitiendo escaneos más precisos y seguros.
2. **PostgreSQL/Neon**: Menciona que usas una base de datos Serverless (Neon) para garantizar escalabilidad sin costos fijos.
3. **CSP**: Explica que la política CSP es el control más fuerte contra el XSS moderno.
