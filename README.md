# API IronFit - Socios

## Descripción

Microservicio encargado de la gestión de socios dentro del sistema IronFit.

Permite:

- Registrar socios
- Consultar socios
- Actualizar información de socios
- Modificar estado de un socio
- Eliminar socios
- Buscar socios por RUT
- Buscar socios por estado
- Buscar socios por edad
- Buscar socios por sucursal
- Consultar socios inactivos según fecha
- Integración con la API Finanzas para consultas financieras

---

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Oracle Database
- Oracle SQL Developer
- Swagger/OpenAPI
- Maven
- Lombok
- Jakarta Validation
- JUnit 5
- Mockito
- Global Exception Handler (`@RestControllerAdvice`)

---

## Configuración de base de datos

Configurar en `application.yml`:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
```

Variables de entorno:

```env
DB_URL=jdbc:oracle:thin:@localhost:1521:xe
DB_USER=TU_USUARIO
DB_PASSWORD=TU_PASSWORD
```

> Para la evaluación también puede configurarse directamente la conexión dentro del `application.yml`.

---

## Script SQL

Crear tabla:

```sql
CREATE TABLE SOCIO (
    ID_SOCIO NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    RUT VARCHAR2(12) NOT NULL UNIQUE,
    NOMBRE VARCHAR2(50) NOT NULL,
    APELLIDO VARCHAR2(50) NOT NULL,
    EDAD NUMBER NOT NULL,
    ESTADO VARCHAR2(20) NOT NULL,
    ULTIMO_ACCESO DATE,
    SUCURSAL VARCHAR2(50)
);
```

---

## Ejecución

- Clonar el repositorio.
- Configurar la conexión a Oracle.
- Ejecutar el script SQL.
- Ejecutar:

```bash
mvn clean install
mvn spring-boot:run
```

---

## Puerto

21502

---

## Swagger

```
http://localhost:21502/swagger-ui/index.html
```

---

# Endpoints principales

## Consultas

- GET /api/v4/socios
- GET /api/v4/socios/{id}
- GET /api/v4/socios/rut/{rut}
- GET /api/v4/socios/estado/{estado}
- GET /api/v4/socios/edad/{edad}
- GET /api/v4/socios/sucursal/{sucursal}
- GET /api/v4/socios/inactivos?fecha={fecha}

## Gestión

- POST /api/v4/socios
- PUT /api/v4/socios/{id}
- PATCH /api/v4/socios/{id}
- DELETE /api/v4/socios/{id}

---

## Integración entre microservicios

La API Socios entrega información utilizada por la API Finanzas para validar el estado de un socio durante la consulta de deudas.

La comunicación entre ambos microservicios se realiza mediante **RestTemplate**.

---

## Manejo global de excepciones

El proyecto implementa un Global Exception Handler para centralizar el manejo de errores.

### Errores gestionados

- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error

---

### Formato de respuesta

```json
{
  "fecha": "2026-07-09T12:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "El socio solicitado no existe",
  "ruta": "/api/v4/socios/99"
}
```

---

### Beneficios

- Centraliza el manejo de excepciones.
- Respuestas consistentes para todos los endpoints.
- Facilita el mantenimiento.
- Mejora el diagnóstico de errores.
- Incrementa la cobertura de pruebas.

---

## Testing

El proyecto incluye pruebas unitarias para:

- Model
- Service
- Controller

### Cobertura

- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error

Se incluyen pruebas para:

- Obtener todos los socios.
- Buscar por ID.
- Buscar por RUT.
- Buscar por estado.
- Buscar por edad.
- Buscar por sucursal.
- Buscar socios inactivos.
- Registrar socios.
- Actualizar información.
- Actualizar estado.
- Eliminar socios.
- Manejo de excepciones mediante Global Exception Handler.
