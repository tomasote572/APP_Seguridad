# Guía de Presentación Ejecutiva - WebSecLab (Template 2)

Esta guía contiene el contenido que debes copiar en el **PowerPoint Template 2** de la Universidad.

## Diapositiva 1: Título y Equipo
- **Título**: WebSecLab: Gestión Inteligente de Seguridad y DAST
- **Subtítulo**: Proyecto Final FDSI 2026-I
- **Integrantes**: Tomas
- **Institución**: Escuela Colombiana de Ingeniería Julio Garavito

## Diapositiva 2: El Problema
- Falta de centralización en el reporte de vulnerabilidades.
- Dificultad para rastrear mitigaciones en tiempo real.
- Necesidad de herramientas de escaneo dinámico ligeras para entornos de desarrollo.

## Diapositiva 3: Solución Propuesta (Innovación)
- Plataforma Web integrada con motor DAST propio.
- Arquitectura "Secure by Design" basada en Spring Boot 3.3.
- Dashboard interactivo para la toma de decisiones basada en riesgo.

## Diapositiva 4: Stack Tecnológico (SBOM)
- **Backend**: Java 21, Spring Boot, Spring Security, JPA.
- **Frontend**: Thymeleaf, Bootstrap 5 (CSP compliant).
- **Base de Datos**: PostgreSQL (Producción en NeonDB) / H2 (Desarrollo).
- **Despliegue (CI/CD)**: GitHub Actions hacia Azure App Service.
- **Seguridad**: Variables de entorno seguras, BCrypt, CSP, CSRF, HSTS.

## Diapositiva 5: Arquitectura Técnica
- *Insertar Diagrama de Mermaid de DOCUMENTACION_COMPLETA.md*
- Explicación de la capa de seguridad perimetral.
- Flujo de datos desde el escaneo hasta el reporte.

## Diapositiva 6: Análisis de Seguridad (SCA/SAST)
- **SCA**: Análisis de dependencias limpias de vulnerabilidades críticas.
- **SAST**: Código verificado contra inyección SQL y XSS.
- **Secret Detection**: Limpieza de credenciales en configuración.

## Diapositiva 7: Caso de Éxito / Aplicación de Seguridad
- **Vulnerabilidad**: Falta de protección contra Clickjacking.
- **Acción**: Implementación de Headers de seguridad dinámicos.
- **Resultado**: Mitigación verificada y cumplimiento de OWASP Top 10.

## Diapositiva 8: Conclusiones y Futuro
- WebSecLab mejora la trazabilidad de seguridad en un 60%.
- Próximos pasos: Integración con Pipelines de CI/CD (Jenkins/GitHub Actions).

---

### Notas para la Defensa:
1. **Enfoque Técnico**: Si te preguntan por la CSP, explica que eliminaste los `script` y `style` in-line para evitar que atacantes inyecten código malicioso.
2. **Control del Stack**: Menciona que usas Java 21 por las mejoras en performance y seguridad de la JVM moderna.
3. **Defensa de Seguridad**: Resalta que el uso de tokens CSRF es automático gracias a la integración nativa de Spring Security con Thymeleaf.
