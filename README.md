API IronFit - Socios
Descripción

Microservicio encargado de la gestión de socios dentro del sistema IronFit.

Este Permite:

Registrar socios
Consultar socios
Actualizar información
Modificar estado
Eliminar socios
Tecnologías utilizadas
Java 21
Spring Boot
Spring Data JPA
Oracle Database
Oracle SQL Developer
Swagger/OpenAPI
Maven
Lombok
Validation API
JUnit 5
Mockito
Global Exception Handler (@RestControllerAdvice)
Configuración de base de datos

Configurar en application.yml:

spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}

Variables de entorno:

DB_URL=jdbc:oracle:thin:@ TU_URL O LOCALHOST
DB_USER=TU_USUARIO
DB_PASSWORD=TU_PASSWORD
Script SQL

Crear tabla:

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

Ejecución
Clonar repositorio
Configurar variables de entorno
Ejecutar script SQL en Oracle
Ejecutar:
mvn clean install
mvn spring-boot:run
Puerto

21502

Swagger

http://localhost:21502/swagger-ui/index.html

Endpoints principales
GET /api/v3/socios
GET /api/v3/socios/{id}
GET /api/v3/socios/rut/{rut}
GET /api/v3/socios/estado/{estado}
GET /api/v3/socios/edad/{edad}
POST /api/v3/socios
PUT /api/v3/socios/{id}
PATCH /api/v3/socios/{id}
DELETE /api/v3/socios/{id}
Manejo global de excepciones

El proyecto implementa un Global Exception Handler mediante @RestControllerAdvice, centralizando el manejo de errores.

Gestiona:

400 Bad Request → errores de validación o datos inválidos.
404 Not Found → socio no encontrado.
500 Internal Server Error → errores inesperados del sistema.

Formato de respuesta:

{
  "fecha": "2026-06-28T12:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "El socio solicitado no existe",
  "ruta": "/api/v3/socios/99"
}

Beneficios:

Centraliza excepciones
Mantiene respuestas consistentes
Mejora trazabilidad
Facilita pruebas
Testing

Incluye pruebas unitarias de:

Model
Service
Controller

Cobertura:

200 OK
201 CREATED
400 BAD REQUEST
404 NOT FOUND
500 INTERNAL SERVER ERROR

Los errores son gestionados por el Global Exception Handler.
