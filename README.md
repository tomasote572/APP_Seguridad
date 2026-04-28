# WebSecLab - Documentación Completa

Esta documentación describe la arquitectura, organización y seguridad del proyecto WebSecLab separado en componentes backend y frontend.

---

## 📁 Estructura General

```
APP_Seguridad/
├── backend/                 # Lógica del negocio (Java, Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/        # Código fuente Java
│   │   │   └── resources/   # Configuración y archivos estáticos
│   │   └── test/            # Tests unitarios
│   ├── target/              # Archivos compilados
│   ├── pom.xml              # Dependencias Maven
│   └── README.md            # Documentación backend
├── frontend/                # Presentación (HTML, CSS, Thymeleaf)
│   ├── src/
│   │   ├── templates/       # Plantillas HTML Thymeleaf
│   │   └── static/          # Estilos CSS e imágenes
│   ├── target/              # Archivos compilados del frontend
│   └── README.md            # Documentación frontend
├── websec-intellij/         # Proyecto original (legacy)
└── README.md                # Documentación principal
```

---

## 🔧 Backend - Lógica del Negocio

El backend contiene toda la lógica de negocio de la aplicación WebSecLab, desarrollado con **Spring Boot** y **Java**.

### Estructura del Código

```
backend/src/main/java/com/example/webseclab/
├── WebSecLabApplication.java        # Punto de entrada de la aplicación
├── config/                          # Configuración de la aplicación
│   ├── DataLoader.java              # Carga de datos iniciales
│   ├── SecurityConfig.java          # Configuración de seguridad
│   ├── StringToFindingConverter.java # Conversor de datos
│   └── StringToWebAssetConverter.java # Conversor de activos
├── controller/                      # Controladores Spring MVC
│   ├── HomeController.java          # Página principal
│   ├── WebAssetController.java      # Gestión de activos web
│   ├── VulnerabilityFindingController.java # Hallazgos de seguridad
│   ├── MitigationController.java    # Mitigaciones
│   └── TrafficLogController.java    # Registros de tráfico
├── model/                           # Entidades JPA
│   ├── WebAsset.java                # Activos web
│   ├── VulnerabilityFinding.java    # Hallazgos de vulnerabilidades
│   ├── Mitigation.java              # Planes de mitigación
│   ├── TrafficLog.java              # Registros de tráfico
│   ├── Severity.java                # Enum de severidad
│   └── AssetType.java               # Enum de tipos de activos
├── repository/                      # Interfaces JPA
│   ├── WebAssetRepository.java
│   ├── VulnerabilityFindingRepository.java
│   ├── MitigationRepository.java
│   └── TrafficLogRepository.java
└── service/                         # Lógica de negocio
    ├── DastScannerService.java      # Escáner DAST
    ├── WebAssetService.java
    ├── VulnerabilityFindingService.java
    ├── MitigationService.java
    ├── TrafficLogService.java
    └── impl/                        # Implementaciones
        ├── WebAssetServiceImpl.java
        ├── VulnerabilityFindingServiceImpl.java
        ├── MitigationServiceImpl.java
        └── TrafficLogServiceImpl.java
```

### Configuración

- **application.properties**: Configuración de Spring Boot, base de datos H2, puerto (8080)
- **SecurityConfig.java**: Configuración completa de seguridad (ver sección de Seguridad)

### Tecnologías

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.3.5** - Framework web
- **Spring Data JPA** - Persistencia de datos
- **Spring Security** - Framework de seguridad
- **Hibernate** - ORM (Object-Relational Mapping)
- **H2 Database** - Base de datos en memoria/archivo local
- **Maven 3.6+** - Gestor de dependencias

### Compilación y Ejecución

```bash
# Compilar el proyecto
cd backend
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run

# La aplicación estará disponible en http://localhost:8080
```

---

## 🎨 Frontend - Presentación

El frontend contiene toda la interfaz de usuario de la aplicación, desarrollado con **Thymeleaf** (renderizado servidor) y **Bootstrap 5**.

### Estructura

```
frontend/src/
├── templates/                   # Plantillas HTML Thymeleaf
│   ├── index.html              # Página de inicio
│   ├── login.html              # Página de inicio de sesión
│   ├── assets.html             # Gestión de activos web
│   ├── findings.html           # Visualización de hallazgos
│   ├── mitigations.html        # Gestión de mitigaciones
│   ├── traffic.html            # Registros de tráfico
│   └── fragments.html          # Componentes reutilizables
└── static/
    └── css/
        ├── styles.css          # Estilos CSS personalizados
        └── login.css           # Estilos de la página de login
```

### Componentes Principales

#### **index.html** - Página de Inicio
- Dashboard con estadísticas generales
- Tabla de hallazgos recientes
- Navegación principal

#### **login.html** - Página de Inicio de Sesión
- Formulario centrado y seguro
- Estilos separados en archivo CSS externo
- Protección CSP (Content-Security-Policy)

#### **assets.html** - Gestión de Activos Web
- Listado de aplicaciones web y APIs
- Registro manual de activos
- Escaneo DAST automático

#### **findings.html** - Hallazgos de Seguridad
- Tabla completa de vulnerabilidades detectadas
- Filtrado por severidad y OWASP
- Detalles de cada hallazgo

#### **mitigations.html** - Planes de Mitigación
- Gestión de planes de remediación
- Seguimiento de estado (Pendiente, En progreso, Resuelto)
- Asignación a hallazgos

#### **traffic.html** - Registros de Tráfico
- Historial de escaneos y conexiones
- IPs de origen y destino
- Protocolos utilizados

#### **fragments.html** - Componentes Reutilizables
- Barra de navegación
- Navegación secundaria
- Modales y componentes comunes

### Estilos

- **styles.css**: Estilos personalizados de WebSecLab
- **login.css**: Estilos específicos de la página de login (separados para cumplir CSP)
- **Bootstrap 5**: Proporciona componentes base responsivos

### Desarrollo

Para modificar la interfaz:

1. **Editar plantillas HTML:**
   ```
   backend/src/main/resources/templates/*.html
   ```

2. **Modificar estilos:**
   ```
   backend/src/main/resources/static/css/*.css
   ```

3. **Reiniciar la aplicación** para ver cambios:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### Notas Importantes

- Las plantillas usan **Thymeleaf** para renderizado del lado del servidor
- Integración automática con controladores Spring Boot
- Los estilos se sirven desde `/static/css/`
- Bootstrap 5 proporciona componentes base responsivos
- Los estilos CSS están en archivos separados para cumplir con la Política de Seguridad de Contenido (CSP)

---

## 🚀 Inicio Rápido

### 1. **Compilar el Proyecto**
```bash
cd backend
mvn clean install
```

### 2. **Ejecutar la Aplicación**
```bash
cd backend
mvn spring-boot:run
```

### 3. **Acceder a la Aplicación**
Abre tu navegador en: **http://localhost:8080**

### 4. **Iniciar Sesión**
- **Usuario**: `admin` o `tomas`
- **Contraseña**: `sistemas26` o `nitro321`

---

## 🧪 Pruebas (Testing)

El proyecto incluye soporte tanto para pruebas unitarias como para pruebas de carga.

### Pruebas Normales (Unitarias y de Integración)
Utilizamos **JUnit 5** y **Spring Boot Test**. Para ejecutar las pruebas automatizadas del proyecto, usa el siguiente comando en la carpeta `backend`:
```bash
mvn test
```

### Pruebas de Carga (Load Testing)
Para verificar la resistencia de la aplicación y la conexión a la base de datos (Neon), se ha incluido un script en la raíz del proyecto llamado `load_test.bat`.

**Cómo ejecutar la prueba de carga:**
1. Asegúrate de que tu servidor Spring Boot esté corriendo (ver Inicio Rápido).
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta el script de prueba pasando un identificador de la prueba (ej: 1):
   ```cmd
   .\load_test.bat 1
   ```
Este script lanzará ráfagas de 100 peticiones concurrentes a la aplicación y te mostrará el porcentaje de éxito frente a la carga.

---

## 📊 Base de Datos

La aplicación utiliza una estrategia de doble entorno para la base de datos:

**1. Entorno de Desarrollo (Local)**
- **Tipo**: H2 Database (Embebida)
- **Ubicación**: `backend/data/webseclab`
- **Persistencia**: Archivo local
- **Consola H2**: Disponible en `http://localhost:8080/h2-console` (requiere rol ADMIN)

**2. Entorno de Producción (Nube)**
- **Tipo**: PostgreSQL (Alojado en Neon.tech)
- **Seguridad**: Conexión SSL (`sslmode=require`) y credenciales inyectadas vía variables de entorno en Azure, protegiendo así los secretos del código fuente.

---

## 🔒 Características Principales

### 1. **Motor DAST Integrado**
Escáner dinámico de seguridad que detecta:
- Fingerprinting de servidor
- Vulnerabilidades de clickjacking
- Ausencia de headers de seguridad (HSTS)

### 2. **Gestión de Activos Web**
- Registro y catalogación de aplicaciones web
- Tipos: WEB o API
- Información técnica detallada

### 3. **Registro de Tráfico**
- Documentación de escaneos realizados
- Rastreo de IPs y protocolos

### 4. **Planes de Mitigación**
- Remediación automática de hallazgos
- Seguimiento de progreso
- Priorización por severidad

---

## 🔐 Seguridad Implementada

La aplicación integra **Spring Security** para proteger los endpoints y recursos, implementando múltiples capas de protección contra vulnerabilidades web comunes.

### 1. **Autenticación**

**Tipo**: Autenticación basada en formulario

**Punto de entrada**: `/login` - Formulario de inicio de sesión personalizado

**Gestión de sesiones**: Spring Security gestiona automáticamente las cookies de sesión

**Usuarios Configurados** (Entorno de desarrollo):
- Usuario: `tomas` | Contraseña: `nitro321` | Rol: `ADMIN`
- Usuario: `admin` | Contraseña: `sistemas26` | Rol: `ADMIN`

**Codificación de Contraseñas**: 
- Algoritmo: `BCryptPasswordEncoder` 
- Características: Salted hashing con 10 rondas (altamente seguro)
- Incluso si la base de datos se compromete, las contraseñas son irreversibles

**Archivo de Configuración**: 
- `backend/src/main/java/com/example/webseclab/config/SecurityConfig.java`

### 2. **Autorización (Control de Acceso)**

**Modelo de Acceso**: Basado en roles

**Rutas Protegidas**: 
- Todas las rutas requieren autenticación excepto `/login`
- Los recursos estáticos (`/css/**`, `/js/**`, `/images/**`) son accesibles públicamente

**Consola H2 Restringida**: 
- Ruta: `/h2-console/**`
- Acceso: Solo usuarios con rol `ADMIN`
- Propósito: Acceso directo a la base de datos (solo para administradores)

**Redirección Automática**: 
- Los usuarios no autenticados que intenten acceder a rutas protegidas son redirigidos a `/login`
- Después de autenticarse, son redirigidos a la página que intentaron acceder

### 3. **Protección contra CSRF (Cross-Site Request Forgery)**

**Estado**: Habilitado por defecto para todas las rutas

**Excepciones**: Deshabilitado solo para `/h2-console/**` (necesario para la consola)

**Implementación**: 
- Tokens CSRF se incluyen automáticamente en formularios Thymeleaf
- Validación automática en todas las solicitudes POST, PUT, DELETE
- Los tokens se almacenan en la sesión del usuario
- Cada token es único y se regenera tras cada uso

**Cómo funciona**:
1. El servidor genera un token único por sesión
2. El token se incluye en cada formulario HTML
3. Cuando se envía el formulario, el token se valida
4. Si no coincide, la solicitud se rechaza

### 4. **Headers de Seguridad HTTP**

La aplicación configura múltiples headers HTTP para mejorar la seguridad:

#### X-XSS-Protection
- **Objetivo**: Prevenir ataques de Cross-Site Scripting (XSS)
- **Configuración**: `X-XSS-Protection: 1; mode=block`
- **Efecto**: El navegador bloquea la ejecución de scripts maliciosos
- **Navegadores afectados**: Internet Explorer, Edge (legacy)

#### X-Frame-Options
- **Objetivo**: Prevenir ataques de Clickjacking
- **Configuración**: `X-Frame-Options: SAMEORIGIN`
- **Efecto**: La aplicación solo puede ser incrustada en iframes dentro de su propio dominio
- **Caso de uso**: Protege contra ataques de clickjacking

#### Content-Security-Policy (CSP)
**Objetivo**: Control granular sobre qué recursos puede cargar el navegador

**Configuración completa**:
```
script-src 'self' https://cdn.jsdelivr.net;
style-src 'self' https://cdn.jsdelivr.net;
img-src 'self' data:;
frame-ancestors 'self';
```

**Significado de cada directiva**:
- `script-src 'self'`: Solo scripts desde el mismo origen
- `script-src https://cdn.jsdelivr.net`: Scripts permitidos desde CDN confiable
- `style-src 'self'`: Solo estilos CSS desde el mismo origen (NO `unsafe-inline`)
- `style-src https://cdn.jsdelivr.net`: Estilos CSS desde CDN confiable
- `img-src 'self' data:`: Imágenes desde el mismo origen o URLs data
- `frame-ancestors 'self'`: La aplicación solo puede ser embebida en iframes de su propio dominio

**Ventajas**:
- CSP es más moderna y flexible que `X-Frame-Options`
- Protege contra inyección de scripts maliciosos
- Previene robo de datos mediante canales laterales
- Notifica al servidor si se intenta violar la política

### 5. **Gestión de Sesiones**

**Duración**: Configurada por defecto en Spring Security (30 minutos de inactividad típicamente)

**Seguridad de Cookies**: 
- Las cookies de sesión incluyen el flag `HttpOnly` (no accesibles desde JavaScript)
- El flag `Secure` se habilita automáticamente en HTTPS
- Previene acceso a través de XSS

**JSESSIONID**: Cookie estándar de Tomcat para tracking de sesiones

**Cierre de Sesión**: 
- Accesible desde `/logout` 
- Limpia cookies y termina sesión de forma segura
- Invalida el token de sesión en el servidor

### 6. **Vulnerabilidades Comunes Prevenidas**

| Vulnerabilidad | Mecanismo de Prevención | Estado |
|---|---|---|
| **XSS (Cross-Site Scripting)** | CSP + X-XSS-Protection + Thymeleaf escaping | ✅ Activo |
| **CSRF (Cross-Site Request Forgery)** | Tokens CSRF únicos por sesión | ✅ Activo |
| **Clickjacking** | X-Frame-Options + CSP frame-ancestors | ✅ Activo |
| **Inyección SQL** | JPA + consultas parametrizadas | ✅ Activo |
| **Acceso No Autorizado** | Autenticación + Autorización por roles | ✅ Activo |
| **Session Hijacking** | Cookies HttpOnly/Secure | ✅ Activo |
| **Brute Force** | Puede implementarse con throttling | ⚠️ No implementado |
| **Exposición de Datos** | HTTPS en producción | ⚠️ Solo en producción |

### 7. **Recomendaciones para Producción**

| Aspecto | Desarrollo | Producción |
|--------|-----------|-----------|
| **Protocolo** | HTTP (puerto 8080) | HTTPS (puerto 443) con certificado válido |
| **Usuarios** | En memoria | Base de datos real con políticas fuertes |
| **Contraseñas** | De prueba simples | Requisitos de complejidad: mayús, minús, números, símbolos |
| **CSP** | Configurada | Más restrictiva si es posible |
| **Logs** | Verbosos en consola | Sanitizados, almacenados de forma segura |
| **CORS** | Permisivo | Restrictivo a orígenes conocidos |
| **Throttling** | No | Implementar rate limiting |
| **Auditoría** | Básica | Registro completo de acciones |
| **Actualizaciones** | No urgentes | Aplicadas regularmente |

### 8. **Cómo Habilitar HTTPS en Producción**

1. Generar o obtener certificado SSL/TLS válido
2. Convertir a formato PKCS12 si es necesario:
   ```bash
   openssl pkcs12 -export -in cert.pem -inkey key.pem -out keystore.p12
   ```
3. Actualizar `application.properties`:
   ```properties
   server.ssl.enabled=true
   server.ssl.key-store=classpath:keystore.p12
   server.ssl.key-store-password=your-password
   server.ssl.key-store-type=PKCS12
   server.port=443
   ```
4. Reiniciar la aplicación

---

## ☁️ Despliegue y CI/CD

El proyecto cuenta con integración y despliegue continuo (CI/CD) automatizado mediante **GitHub Actions** hacia **Microsoft Azure**.

### Arquitectura de Producción
- **Hosting**: Azure App Service (Plan B1 Linux)
- **Runtime**: Java SE (Java 21)
- **Pipeline**: El archivo `.github/workflows/azure-deploy.yml` compila automáticamente la aplicación con Maven y sube el `.jar` resultante a Azure cada vez que hay un `push` a la rama `main`.
- **Variables de Entorno**: Azure gestiona el puerto (`WEBSITES_PORT=8080`) y las credenciales de la base de datos PostgreSQL, evitando tener contraseñas hardcodeadas en el código fuente.

---

## 🛠️ Dependencias Principales

### Backend
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Security
- Hibernate ORM
- H2 Database
- Apache Tomcat (embedded)
- Maven 3.6+

### Frontend
- Thymeleaf (template engine)
- Bootstrap 5.3.0
- HTML5
- CSS3

---

## 📝 Notas de Desarrollo

- El proyecto mantiene estructura modular separada backend/frontend
- El frontend se compila e incluye automáticamente en el build Maven
- Todos los datos se persisten en H2 Database
- La aplicación es completamente funcional standalone (no requiere servidor externo)
- La seguridad está centralizada en `SecurityConfig.java`
- Los cambios en seguridad requieren reinicio del servidor

---

## 🔧 Solución de Problemas

### El puerto 8080 ya está en uso
```bash
# Windows: Encontrar proceso
netstat -ano | findstr :8080

# Terminar el proceso
taskkill /PID <PID> /F
```

### Problemas con CSP
- Asegúrate de que todos los estilos CSS estén en archivos separados (no en línea)
- Verifica la consola del navegador para errores de CSP
- Las directivas de CSP pueden ser ajustadas en `SecurityConfig.java`

### Sesión de usuario cerrada inesperadamente
- Puede ser por timeout de sesión (30 minutos por defecto)
- O por reinicio del servidor
- En producción, considera persistencia de sesiones en base de datos

---

## 📞 Contacto y Soporte

**Autor**: Tomas (Proyecto Final FDSI 2026-I)
Para problemas, sugerencias o mejoras en la seguridad, contacta al desarrollador.

