# Market Backend 2026

Backend academico en Java y Spring Boot para un catalogo de productos y compras. Practica arquitectura por capas, persistencia JPA, mapeo con MapStruct y documentacion OpenAPI.

## Stack

| Capa | Tecnologia |
|---|---|
| Runtime | Java 25 |
| Framework | Spring Boot 3.5 |
| Persistencia | Spring Data JPA |
| Base de datos | H2 local, PostgreSQL runtime |
| Mapeo | MapStruct |
| Documentacion API | springdoc-openapi |
| Build | Gradle Wrapper |

## Estructura

```text
src/main/java/mx/edu/tecdesoftware/market_backend/
├── domain/          # Modelos y contratos de dominio
├── persistence/     # Entidades, repositorios JPA y mappers
└── persistence/web/ # Controladores REST y DTOs
```

## Ejecucion local

```bash
./gradlew bootRun
```

Swagger UI:

```text
http://localhost:8090/swagger-ui.html
```

## Pruebas

```bash
./gradlew test
```

## Notas

- `src/main/resources/data.sql` carga datos iniciales para pruebas locales.
- No subir credenciales reales de base de datos; usar variables de entorno o perfiles locales ignorados.

## Derechos

Codigo publicado para revision profesional. Sin licencia de reutilizacion; todos los derechos reservados.
