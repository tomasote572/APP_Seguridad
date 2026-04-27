# WebSecLab - Estructura del Proyecto

Esta documentación describe la arquitectura y organización del proyecto WebSecLab separado en componentes backend y frontend.

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

### Tecnologías

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.3.5** - Framework web
- **Spring Data JPA** - Persistencia de datos
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
│   ├── assets.html             # Gestión de activos web
│   ├── findings.html           # Visualización de hallazgos
│   ├── mitigations.html        # Gestión de mitigaciones
│   ├── traffic.html            # Registros de tráfico
│   └── fragments.html          # Componentes reutilizables
└── static/
    └── css/
        └── styles.css          # Estilos CSS personalizados
```

### Componentes Principales

#### **index.html** - Página de Inicio
- Dashboard con estadísticas generales
- Tabla de hallazgos recientes
- Navegación principal

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
- Integración con **Bootstrap 5** para componentes responsivos

### Desarrollo

Para modificar la interfaz:

1. **Editar plantillas HTML:**
   ```
   frontend/src/templates/*.html
   ```

2. **Modificar estilos:**
   ```
   frontend/src/static/css/styles.css
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

---

## 📊 Base de Datos

- **Tipo**: H2 Database (Embebida)
- **Ubicación**: `backend/data/webseclab`
- **Persistencia**: Archivo local (los datos se mantienen entre reinicios)
- **Consola H2**: Disponible en `http://localhost:8080/h2-console`

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

## 🛠️ Dependencias Principales

### Backend
- Spring Boot 3.3.5
- Spring Data JPA
- Hibernate
- H2 Database
- Maven

### Frontend
- Thymeleaf
- Bootstrap 5
- HTML5
- CSS3

---

## 📝 Notas de Desarrollo

- El proyecto mantiene estructura modular separada backend/frontend
- El frontend se compila e incluye automáticamente en el build Maven
- Todos los datos se persisten en H2 Database
- La aplicación es completamente funcional standalone (no requiere servidor externo)

