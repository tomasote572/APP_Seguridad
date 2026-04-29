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
- **URL**: [webseclab-fzazcucea8crd7e0.canadacentral-01.azurewebsites.net][webseclab-fzazcucea8crd7e0.canadacentral-01.azurewebsites.net]
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

### Cómo sincronizar la Base de Datos (Local vs NeonDB)
- **Uso Local Puro (H2 Database)**: Ejecuta `mvn spring-boot:run`. Los datos se guardarán en tu propio disco duro.
- **Sincronizar Local con la Nube (NeonDB)**: Desde la carpeta `backend`, ejecuta el script inyector mediante el comando:
  ```cmd
  .\iniciar_neon.bat
  ```
- **Sincronizar Producción (Azure) con NeonDB**: Ve a la sección de **Configuración (Variables de entorno)** de tu App Service en Azure y agrega las siguientes variables para que el servidor se conecte a la nube:
- `DB_URL`: `jdbc:postgresql://<host-neon>/neondb?sslmode=require`
- `DB_USER`: `<usuario>`
- `DB_PASS`: `<contraseña>`
- `DB_DRIVER`: `org.postgresql.Driver`
- `DB_DIALECT`: `org.hibernate.dialect.PostgreSQLDialect`

### 🔍 Comandos de Logs en Tiempo Real (Azure)
Si hay un error 500 en producción, puedes ver la terminal en vivo desde el Portal de Azure (**Supervisión > Flujo de registros**) o ejecutando este comando desde tu terminal local con Azure CLI:
```bash
az webapp log tail --name webseclab-fzazcucea8crd7e0 --resource-group <Tu-Grupo-De-Recursos>
```

---

## 🧪 Cómo correr las Pruebas (Testing)

- **Pruebas Unitarias (JUnit 5)**: Ejecuta `mvn test` dentro de la carpeta `backend/`.
- **Pruebas de Carga**: Ejecuta el script `.\load_test.bat 1` desde la terminal en la raíz del proyecto. Este script lanzará ráfagas de peticiones concurrentes para verificar la resiliencia del servidor y de la conexión a NeonDB sin caídas.

---

## 🎯 Conclusiones del Proyecto

1. **Centralización y Automatización DAST**: La integración de un motor de escaneo dinámico directamente en la plataforma permite hacer *fingerprinting* y descubrir vulnerabilidades tecnológicas en tiempo real sin depender de herramientas externas pesadas.
2. **Arquitectura "Secure by Design"**: Al implementar proactivamente políticas estrictas como CSP, tokens CSRF y autenticación BCrypt, la aplicación neutraliza desde su concepción las amenazas más críticas definidas por el OWASP Top 10.
3. **Escalabilidad y Resiliencia en la Nube**: La separación de entornos (H2 local vs PostgreSQL NeonDB en la nube) combinada con el despliegue automatizado en Azure, garantiza un sistema de alta disponibilidad capaz de soportar pruebas de carga severas manteniendo un 100% de éxito en sus respuestas.