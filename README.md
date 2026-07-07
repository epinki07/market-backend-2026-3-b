# market-backend

Backend academico en Java con Spring Boot para modelar una API de productos de mercado. El proyecto esta orientado a practicar arquitectura por capas, entidades JPA, repositorios, mapeo entre persistencia y dominio, y preparacion de endpoints REST.

## Objetivo

- Construir la base de un backend para catalogo de productos.
- Separar dominio, persistencia y capa web.
- Practicar Spring Boot, Spring Data JPA y PostgreSQL.
- Usar MapStruct para convertir entidades de persistencia a modelos de dominio.

## Stack

- Java 25
- Spring Boot 4.0.7-SNAPSHOT
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Gradle
- MapStruct

## Estructura

```text
src/main/java/mx/edu/tecdesoftware/market_backend/
├── MarketBackendApplication.java
├── domain/
│   ├── repository/       Contrato de repositorio de dominio
│   └── service/          Modelo y servicio de productos
├── persistence/
│   ├── crud/             Repositorios Spring Data
│   ├── entity/           Entidades JPA
│   ├── mapper/           Mapeadores MapStruct
│   └── ProductoRepository.java
└── persistence/web/
    └── controller/       Controladores REST
```

## Estado actual

El repositorio contiene la base de arquitectura y persistencia:

- Entidades JPA para `Producto`, `Categoria`, `Cliente`, `Compra` y relacion compra-producto.
- Repositorio de dominio `ProductRepository`.
- Implementacion de persistencia `ProductoRepository`.
- Servicio `ProductService` con operaciones de consulta, guardado y eliminacion.
- Mapeadores MapStruct para productos y categorias.
- Controlador REST preparado en `/products`.

El controlador esta en etapa inicial; los metodos HTTP pueden completarse sobre el servicio existente.

## Configuracion

El archivo principal de configuracion esta en:

```text
src/main/resources/application.properties
```

Actualmente define:

```properties
spring.application.name=market-backend
spring.profiles.active=dev
```

Para ejecutar con base de datos real se debe agregar una configuracion local de PostgreSQL en un archivo ignorado por Git, por ejemplo `application-local.properties`, o mediante variables de entorno.

## Como ejecutar

Desde la raiz del proyecto:

```bash
./gradlew bootRun
```

Para correr pruebas:

```bash
./gradlew test
```

## Siguientes pasos recomendados

- Implementar endpoints en `ProductController`.
- Agregar DTOs para requests y responses.
- Configurar perfil local de PostgreSQL.
- Crear pruebas de servicio y controlador.
- Documentar ejemplos de request/response cuando los endpoints esten completos.

## Autor

Diego Ramirez Magana
