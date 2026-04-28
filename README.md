# WebSecLab - Proyecto Final FDSI 2026-I

**Autores:** Tomas Ramirez, Kevyn Forero, Juliana

Plataforma integral para la gestión de hallazgos de seguridad web y escaneo DAST automatizado, construida con una arquitectura *Secure by Design*.

---

## 🚀 Cómo correr el proyecto (Desarrollo Local)

### 1. Compilar y Ejecutar
Abre tu terminal en la raíz del proyecto y ejecuta:
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 2. Acceder a la Aplicación
- **URL**: [http://localhost:8080](http://localhost:8080)
- **Credenciales (Admin)**: `tomas` / `nitro321` o `admin` / `sistemas26`

---

## 📁 Estructura del Código

El proyecto está diseñado de forma modular, concentrando la seguridad y lógica principal en el entorno Backend de Spring Boot:

```text
APP_Seguridad/
├── backend/                             # Aplicación Principal (Java, Spring Boot)
│   ├── src/main/java/com/example/webseclab/
│   │   ├── config/                      # Configuración de Seguridad (SecurityConfig)
│   │   ├── controller/                  # Controladores Spring MVC
│   │   ├── model/                       # Entidades JPA (Activos, Hallazgos, etc.)
│   │   ├── repository/                  # Interfaces de acceso a datos
│   │   └── service/                     # Motor DAST y lógica de negocio
│   ├── src/main/resources/
│   │   ├── static/css/                  # Estilos CSS separados (CSP compliant)
│   │   ├── templates/                   # Vistas HTML Thymeleaf
│   │   └── application.properties       # Variables de entorno y DB local
│   └── iniciar_neon.bat                 # Script inyector de credenciales locales
├── docs/                                # Documentos IEEE, Reportes, Presentaciones
├── frontend/                            # Interfaz legada 
└── load_test.bat                        # Script de pruebas de estrés/carga
```

---

## 📦 Dependencias Principales

### Backend
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Security
- Hibernate ORM
- H2 Database (Local) / PostgreSQL NeonDB (Producción)
- Apache Tomcat (embedded)
- Maven 3.6+
- Java 21

### Frontend
- Thymeleaf (template engine)
- Bootstrap 5.3.0
- HTML5
- CSS3

---

## 🔒 Características de Seguridad Implementadas
El proyecto implementa protección robusta contra vulnerabilidades del **OWASP Top 10**:
- **Motor DAST Integrado**: 
  - **Mecanismo de Escaneo**: Cuando el usuario ingresa una URL, el sistema lanza peticiones HTTP ligeras (`HEAD`) hacia el objetivo. 
  - **Análisis de Cabeceras**: Extrae e inspecciona los headers de respuesta (`Server`, `X-Powered-By`) para hacer un *fingerprinting* automático de la tecnología subyacente (ej. detectando Apache, Nginx o frameworks específicos).
  - **Pruebas y Mitigación OWASP**: La herramienta inspecciona la respuesta HTTP y genera hallazgos automáticos basados en las siguientes pruebas:
    - **Fingerprinting de Servidor**: Lee los headers `Server` y `X-Powered-By` para alertar sobre la exposición excesiva de información tecnológica, lo cual facilita ataques dirigidos. **Mitiga: OWASP A05:2021 (Configuración de Seguridad Incorrecta)**.
    - **Test de Clickjacking**: Verifica la presencia del header `X-Frame-Options`. Si falta, alerta que la URL puede ser embebida maliciosamente en un `iframe` invisible. **Mitiga: OWASP A05:2021 / A04:2021 (Diseño Inseguro)**.
    - **Test de HSTS (Strict-Transport-Security)**: Comprueba si el objetivo exige conexión HTTPS estricta. Su ausencia permite ataques de interceptación (*Man-in-the-Middle*) y *Downgrade*. **Mitiga: OWASP A02:2021 (Fallos Criptográficos)**.
- **Autenticación Fuerte**: Encriptación irreversible de contraseñas mediante `BCrypt`. **Mitiga: OWASP A07:2021 (Fallos de Identificación y Autenticación)**.
- **Protección CSRF**: Tokens únicos generados e inyectados automáticamente en cada sesión. **Mitiga: OWASP A01:2021 (Pérdida de Control de Acceso)**.
- **Content-Security-Policy (CSP) Estricta**: Estilos y scripts separados (sin código in-line) para prevenir ataques XSS. **Mitiga: OWASP A03:2021 (Inyección)**.
- **Anti-Clickjacking**: Configuración de `X-Frame-Options: SAMEORIGIN` y `frame-ancestors`. **Mitiga: OWASP A04:2021 (Diseño Inseguro)**.

### Vulnerabilidades Comunes Prevenidas (OWASP Top 10)

| Vulnerabilidad | Categoría OWASP Top 10 | Mecanismo de Prevención | Estado |
|---|---|---|---|
| **XSS (Cross-Site Scripting)** | **A03:2021 (Inyección)** | CSP + X-XSS-Protection + Thymeleaf escaping | ✅ Activo |
| **CSRF** | **A01:2021 (Control de Acceso)** | Tokens CSRF únicos por sesión | ✅ Activo |
| **Clickjacking** | **A04:2021 (Diseño Inseguro)** | X-Frame-Options + CSP frame-ancestors | ✅ Activo |
| **Inyección SQL** | **A03:2021 (Inyección)** | JPA + consultas parametrizadas | ✅ Activo |
| **Acceso No Autorizado** | **A01:2021 (Control de Acceso)**| Autenticación + Autorización por roles | ✅ Activo |
| **Session Hijacking** | **A07:2021 (Autenticación)** | Cookies HttpOnly/Secure | ✅ Activo |
| **Exposición de Datos** | **A02:2021 (Fallos Criptográficos)**| Conexión HTTPS en Azure y NeonDB | ✅ Activo |

---

## ☁️ Despliegue en Azure y Base de Datos (NeonDB)

El despliegue está automatizado vía **GitHub Actions** hacia Azure App Service.

### Cómo sincronizar la Base de Datos en Producción
Para que Azure se conecte a la base de datos PostgreSQL, debes ir a la sección de **Configuración (Variables de entorno)** de tu App Service en Azure y agregar:
- `DB_URL`: `jdbc:postgresql://<host-neon>/neondb?sslmode=require`
- `DB_USER`: `<usuario>`
- `DB_PASS`: `<contraseña>`
- `DB_DRIVER`: `org.postgresql.Driver`
- `DB_DIALECT`: `org.hibernate.dialect.PostgreSQLDialect`

### 🔍 Logs en Tiempo Real (Azure)
Si hay un error 500 en producción, ve a tu App Service en el Portal de Azure y entra a **Supervisión > Flujo de registros (Log stream)** para ver la terminal de Spring Boot en vivo.

---

## 🧪 Cómo correr las Pruebas (Testing)

- **Pruebas Unitarias (JUnit 5)**: Ejecuta `mvn test` dentro de la carpeta `backend/`.
- **Pruebas de Carga**: Ejecuta el script `.\load_test.bat 1` desde la terminal en la raíz del proyecto. Este script lanzará ráfagas de peticiones concurrentes para verificar la resiliencia del servidor y de la conexión a NeonDB sin caídas.