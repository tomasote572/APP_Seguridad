# WebSecLab - Laboratorio de Seguridad Web (Spring Boot)

WebSecLab es una aplicación web académica diseñada para documentar, gestionar y automatizar el descubrimiento de hallazgos de seguridad (vulnerabilidades) en aplicaciones web y APIs. 

## 🚀 Características Principales

### 1. Motor DAST (Dynamic Application Security Testing) Integrado
La aplicación cuenta con un escáner dinámico de seguridad integrado capaz de analizar cualquier URL ingresada. 
* **Escaneo Automatizado:** Simplemente ingresa la URL y la herramienta realizará comprobaciones automatizadas de seguridad.
* **Pruebas Realizadas (Testing DAST):**
  1. **Fingerprinting de Servidor:** Inspecciona las cabeceras HTTP buscando la exposición de la tecnología subyacente (cabecera `Server`). Esto previene el Information Disclosure.
  2. **Clickjacking (UI Redressing):** Verifica la existencia de la política `X-Frame-Options`. Si está ausente, indica vulnerabilidad crítica de diseño en la UI.
  3. **HSTS (Seguridad de Transporte Estricta):** Comprueba que las conexiones sobre HTTPS apliquen obligatoriamente la cabecera `Strict-Transport-Security` para evitar ataques Man-In-The-Middle y Downgrade.
* **Generación en Cascada:** Al escanear una URL, la aplicación automáticamente registra el tráfico generado y crea **Planes de Mitigación** preventivos para cada hallazgo detectado.

### 2. Gestión de Activos Web
* **Escaneo Rápido:** Permite registrar un activo y escanearlo con un solo clic ingresando su URL.
* **Registro Manual:** Si prefieres, puedes detallar manualmente la tecnología (ej. PHP, React, Java), nombre, tipo (WEB o API) y descripción detallada del activo.

### 3. Registro de Tráfico (Traffic Logs)
Documenta de forma transparente cada intento de escaneo DAST o conexiones de red sospechosas, registrando la IP de origen, IP de destino, y protocolos utilizados.

### 4. Mitigaciones y Remediación
No solo encuentra vulnerabilidades, sino que documenta la solución. Para cada hallazgo se puede asignar (o autogenerar) un plan de mitigación detallado, con su nivel de prioridad y estado (Pendiente, En progreso, Resuelto).

---

## 🏗️ Arquitectura y Tecnologías
Esta aplicación sigue una arquitectura monolítica clásica MVC (Modelo-Vista-Controlador):
* **Backend:** Spring Boot (Java 21)
* **Frontend:** Thymeleaf + Bootstrap 5 (Renderizado del lado del servidor)
* **Base de Datos:** H2 Database (Configurada en modo local persistente para no perder los datos al apagar el servidor)
* **Persistencia:** Spring Data JPA / Hibernate

---

## 🛠️ Cómo Correr el Proyecto

### Opción 1: Desde la Terminal (Recomendado)
1. Abre tu terminal (PowerShell, CMD o Bash).
2. Navega hasta la carpeta interna del proyecto: `cd websec-intellij`
3. Ejecuta el comando de Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Espera a ver el logo de Spring Boot y el mensaje de arranque.

### Opción 2: Desde IntelliJ IDEA
1. Abre IntelliJ IDEA y selecciona **Open**.
2. Abre la carpeta `websec-intellij`.
3. Deja que Maven sincronice y descargue las dependencias.
4. Navega a `src/main/java/com/example/webseclab/WebSecLabApplication.java`.
5. Haz clic derecho y selecciona **Run 'WebSecLabApplication'**.

---

## 💻 Uso de la Aplicación

1. **Accede al portal:** Abre tu navegador y dirígete a [http://localhost:8080](http://localhost:8080)
2. **Pestaña Activos:** Agrega la URL de tu proyecto mediante el panel de **Escaneo DAST Rápido** para ejecutar pruebas dinámicas, o regístralo con detalle usando el **Nuevo Activo Manual**.
3. **Pestaña Hallazgos:** Revisa las vulnerabilidades descubiertas por el DAST o añade nuevas manualmente.
4. **Pestaña Mitigaciones:** Observa los planes de acción recomendados por el sistema para solucionar las vulnerabilidades encontradas.
5. **Consola H2:** Puedes ver los datos directamente en la base de datos ingresando a `http://localhost:8080/h2-console`
   * **JDBC URL:** `jdbc:h2:file:./data/webseclab`
   * **Usuario:** `sa`
   * **Contraseña:** *(dejar vacío)*

---

## ⚠️ Nota Ética y Académica
Esta herramienta ha sido desarrollada con propósitos puramente académicos para el aprendizaje de gestión de vulnerabilidades y modelado de amenazas. Las pruebas automatizadas (DAST) realizan peticiones HTTP básicas de inspección de cabeceras. **No debe utilizarse para realizar ataques de fuerza bruta, explotación de fallos (exploits) o denegación de servicio sobre entornos de terceros sin su autorización explícita.**
