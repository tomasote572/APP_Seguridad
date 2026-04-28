# WebSecLab: Un Enfoque Integral para la Gestión de Hallazgos de Seguridad Web y Escaneo DAST Automatizado

**Autor**: Tomas

**Resumen**— El presente artículo describe el desarrollo y la implementación de WebSecLab, una plataforma diseñada para centralizar la gestión de vulnerabilidades web detectadas mediante técnicas de Dynamic Application Security Testing (DAST). La aplicación, construida sobre un stack robusto de Spring Boot y Java 21, integra controles de seguridad avanzados como Content Security Policy (CSP), protección contra CSRF y cifrado de contraseñas mediante BCrypt. El estudio detalla la arquitectura del sistema, la metodología de desarrollo siguiendo el ciclo de vida de seguridad y los resultados obtenidos tras la implementación de controles mitigantes ante vulnerabilidades comunes del OWASP Top 10.

**Palabras Clave**— DAST, Seguridad Web, Spring Security, OWASP, Gestión de Vulnerabilidades.

## I. INTRODUCCIÓN

En el panorama actual de la ciberseguridad, la detección temprana de vulnerabilidades es crítica para la integridad de los activos digitales. WebSecLab surge como una respuesta a la necesidad de herramientas académicas y profesionales que no solo detecten fallos, sino que permitan realizar un seguimiento exhaustivo de los planes de mitigación. Este proyecto se enfoca en la automatización del escaneo dinámico, la detección automática de la pila tecnológica y la implementación de una arquitectura defensiva multicapa probada bajo estrés.

## II. METODOLOGÍA

Se empleó una metodología de desarrollo ágil con un enfoque de "Security by Design". Las fases incluyeron:
1. **Identificación de Requerimientos**: Definición de activos web y tipos de hallazgos.
2. **Diseño de Arquitectura**: Implementación de un patrón MVC con una capa de seguridad perimetral controlada por Spring Security.
3. **Análisis de Seguridad**: Ejecución de análisis estático (SAST) y de dependencias (SCA) para asegurar la integridad del stack tecnológico.
4. **Implementación de Controles**: Configuración de headers HTTP de seguridad y políticas de contenido restrictivas.

## III. ARQUITECTURA DEL SISTEMA

La arquitectura se basa en micro-componentes desacoplados que facilitan la escalabilidad:
- **Backend**: Motor Java 21 con Spring Boot, utilizando JPA para la persistencia en una base de datos H2.
- **Frontend**: Interfaz responsiva utilizando Thymeleaf y Bootstrap 5, diseñada para ser compatible con políticas CSP estrictas.
- **Capa de Seguridad**: Configuración centralizada que gestiona la autenticación, autorización y protección contra ataques de inyección y falsificación de peticiones.

## IV. ANÁLISIS DE SEGURIDAD (SCA & SAST)

Se realizó un análisis de composición de software (SCA) sobre el archivo `pom.xml`, identificando las versiones exactas de las librerías. No se detectaron vulnerabilidades críticas en el núcleo de Spring Boot 3.3.5. Asimismo, se implementó un proceso de detección de secretos para evitar la exposición de credenciales en el código fuente.

## V. RESULTADOS

La implementación de WebSecLab permitió:
- Reducir el tiempo de registro de hallazgos mediante el motor DAST integrado y la detección automática de tecnología basada en cabeceras HTTP en tiempo real.
- Centralizar la gestión de mitigaciones, mejorando la trazabilidad del estado de las vulnerabilidades (Pendiente, En Progreso, Resuelto).
- Validar la efectividad de los headers de seguridad mediante pruebas de penetración manuales, confirmando el bloqueo de frames externos (anti-clickjacking) y scripts no autorizados.
- Demostrar alta disponibilidad y resiliencia del entorno Spring Boot conectado a una base de datos distribuida (PostgreSQL en Neon.tech) mediante pruebas de carga concurrentes que mantuvieron un 100% de tasa de éxito bajo estrés.

## VI. CONCLUSIONES

WebSecLab demuestra que la integración de herramientas de escaneo dinámico con una gestión de mitigaciones estructurada fortalece significativamente la postura de seguridad de una organización. La arquitectura modular adoptada permite la evolución continua del sistema frente a nuevas amenazas emergentes.

## REFERENCIAS

[1] OWASP Foundation, "OWASP Top 10:2021," 2021.
[2] Spring Projects, "Spring Security Reference," 2024.
[3] IEEE Standards Association, "IEEE Standard for Software and System Test Documentation," 2022.
