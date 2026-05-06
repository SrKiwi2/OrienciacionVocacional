<div align="center">

# 🎓 Sistema de Orientación Vocacional

### Plataforma inteligente de orientación vocacional con análisis por Inteligencia Artificial

*Guía a los estudiantes hacia su vocación profesional mediante cuestionarios psicopedagógicos analizados por IA.*

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)](https://www.thymeleaf.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![IA](https://img.shields.io/badge/Inteligencia_Artificial-API_IA-FF6F00?style=for-the-badge&logo=openai&logoColor=white)]()
[![Servidor Privado](https://img.shields.io/badge/Deploy-Servidor_Institucional-2C3E50?style=for-the-badge&logo=linux&logoColor=white)]()

[🐛 Reportar Bug](https://github.com/Srkiwi2/OrientacionVocacional/issues) · [✨ Solicitar Feature](https://github.com/Srkiwi2/OrientacionVocacional/issues)

</div>

---

## 📸 Capturas del Sistema

> 🖼️ *Screenshots próximamente...*

---

## 🧩 ¿Qué problema resuelve?

Muchos estudiantes llegan a la universidad sin tener claridad sobre qué carrera estudiar, lo que genera deserción, frustración y pérdida de tiempo y recursos. El proceso tradicional de orientación vocacional es lento, subjetivo y depende exclusivamente de la disponibilidad del psicopedagogo.

**Este sistema automatiza y potencia ese proceso** combinando el conocimiento experto de un psicopedagogo con la capacidad de análisis de la **Inteligencia Artificial**, permitiendo orientar a cientos de estudiantes de forma simultánea, precisa y fundamentada.

---

## ✨ Funcionalidades Principales

### 🧠 Motor de Orientación con IA
- [x] Cuestionarios diseñados y configurados por el **psicopedagogo** de la institución
- [x] Respuestas de estudiantes enviadas y analizadas por una **API de Inteligencia Artificial**
- [x] La IA interpreta las respuestas siguiendo el **criterio y guía del psicopedagogo**
- [x] Resultado personalizado de orientación vocacional por estudiante
- [x] Análisis profundo del perfil: intereses, habilidades y aptitudes

### 📋 Módulo de Instrumentos Vocacionales
- [x] Gestión de múltiples **instrumentos psicopedagógicos** (tests, cuestionarios, escalas)
- [x] Cada instrumento tiene su propio contexto, composición y propósito
- [x] Configurados y administrados directamente por el psicopedagogo
- [x] Aplicación en **cursos de orientación vocacional** grupales o individuales

### 👩‍🏫 Panel del Psicopedagogo
- [x] Creación y gestión de preguntas e instrumentos
- [x] Definición de criterios de interpretación para la IA
- [x] Visualización de resultados por estudiante
- [x] Seguimiento del progreso de cursos vocacionales

### 🎒 Portal del Estudiante
- [x] Acceso simple e intuitivo al cuestionario asignado
- [x] Respuesta guiada paso a paso
- [x] Visualización de su resultado de orientación vocacional
- [x] Historial de instrumentos completados

---

## 🔄 Flujo del Sistema

```
┌─────────────────────────────────────────────────────────┐
│                   FLUJO DE ORIENTACIÓN                   │
└─────────────────────────────────────────────────────────┘

  👩‍🏫 Psicopedagogo          🎒 Estudiante            🤖 IA
       │                          │                     │
       ▼                          │                     │
  Diseña preguntas                │                     │
  y criterios de                  │                     │
  interpretación                  │                     │
       │                          │                     │
       ▼                          ▼                     │
  Configura el          Responde el cuestionario        │
  instrumento           vocacional en el sistema        │
       │                          │                     │
       └──────────────────────────┘                     │
                         │                              │
                         ▼                              │
              Sistema envía respuestas ──────────────► API IA
                    + criterios del                     │
                    psicopedagogo                       │
                                                        ▼
                                             Analiza e interpreta
                                             el perfil vocacional
                                                        │
                         ◄──────────────────────────────┘
                         │
                         ▼
              📄 Resultado personalizado
                 de orientación vocacional
                 entregado al estudiante
```

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| **Backend** | Java 17 + Spring Boot 3 |
| **Frontend** | Thymeleaf + HTML/CSS/JS |
| **Base de Datos** | PostgreSQL |
| **Inteligencia Artificial** | API externa de IA (análisis de texto) |
| **Infraestructura** | Servidor privado institucional |
| **Build** | Maven |

---

## 🚀 Instalación y Ejecución Local

### Prerrequisitos

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL 14+](https://www.postgresql.org/download/)
- API Key del servicio de IA utilizado

### Pasos

**1. Clona el repositorio**
```bash
git clone https://github.com/Srkiwi2/OrientacionVocacional.git
cd OrientacionVocacional
```

**2. Crea la base de datos**
```sql
CREATE DATABASE orientacion_vocacional;
```

**3. Configura la aplicación**

Edita `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orientacion_vocacional
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update

# API de Inteligencia Artificial
ia.api.key=TU_API_KEY
ia.api.url=URL_DEL_SERVICIO_IA
```

**4. Ejecuta el proyecto**
```bash
mvn clean install
mvn spring-boot:run
```

**5. Accede al sistema**
```
http://localhost:8080
```

---

## 📁 Estructura del Proyecto

```
OrientacionVocacional/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/orientacionvocacional/
│   │   │       ├── controller/       # Controladores MVC
│   │   │       ├── model/            # Entidades JPA
│   │   │       ├── repository/       # Repositorios JPA
│   │   │       ├── service/          # Lógica de negocio
│   │   │       └── ia/               # Integración con API de IA
│   │   └── resources/
│   │       ├── templates/            # Vistas Thymeleaf
│   │       │   ├── estudiante/       # Portal del estudiante
│   │       │   ├── psicopedagogo/    # Panel administrativo
│   │       │   └── resultados/       # Vista de resultados
│   │       ├── static/               # CSS, JS, imágenes
│   │       └── application.properties
├── pom.xml
└── README.md
```

---

## 👥 Roles del Sistema

| Rol | Descripción |
|-----|------------|
| 👩‍🏫 **Psicopedagogo** | Diseña instrumentos, preguntas y criterios de interpretación para la IA |
| 🎒 **Estudiante** | Responde cuestionarios y recibe su resultado de orientación vocacional |
| 👔 **Administrador** | Gestiona usuarios, cursos y configuración general del sistema |

---

## 🤖 Integración con Inteligencia Artificial

El corazón del sistema es su motor de análisis con IA. El proceso funciona así:

1. El psicopedagogo define **qué buscar** en las respuestas (criterios, patrones, indicadores)
2. El estudiante responde el cuestionario diseñado por el experto
3. El sistema empaqueta las respuestas junto con los **criterios del psicopedagogo** y los envía a la API de IA
4. La IA analiza el perfil y genera un **resultado personalizado**, siguiendo el pensamiento y guía del experto humano
5. El resultado es presentado al estudiante de forma clara y comprensible

> 💡 Este enfoque combina lo mejor de dos mundos: **el conocimiento experto humano** del psicopedagogo con la **escala y velocidad** de la Inteligencia Artificial.

---

## 👤 Autor

**Srkiwi2** y demas compañeros de trabajo

[![GitHub](https://img.shields.io/badge/GitHub-Srkiwi2-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Srkiwi2)

---

## 📄 Licencia

Desarrollado como sistema institucional de apoyo psicopedagógico.
Todos los derechos reservados © 2024 Srkiwi2.

---

<div align="center">

*Conectando estudiantes con su vocación mediante tecnología e inteligencia artificial — Bolivia 🇧🇴*

</div>
