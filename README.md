# Market Backend

Backend academico en Java con Spring Boot para practicar una API REST de productos y compras.

## Cambios aplicados en esta copia

- Se corrigio el ciclo de MapStruct en `CategoryMapper`.
- Se alineo `Producto.estado` como `boolean`, porque el repositorio lo consulta con `true`/`false`.
- Se agrego el mapeo `idCategoria` <-> `categoryId` en `ProductMapper`.
- Se completo `ProductController` con endpoints REST basicos.
- Se agrego Swagger/OpenAPI con `springdoc-openapi-starter-webmvc-ui`.
- Se ajusto Spring Boot a la linea `3.5.x`, compatible con springdoc `2.8.x`.
- Se agrego `application.properties` con puerto `8090`.
- Se agrego H2 en memoria para que el proyecto pueda arrancar sin PostgreSQL.
- Se agregaron datos semilla en `src/main/resources/data.sql`.
- Se corrigio `Cliente.id` a `String` para coincidir con `Compra.idCliente`.
- Se agrego `equals` y `hashCode` en la llave compuesta `CompraProductoPK`.

## Requisitos

- Java 25, porque el `build.gradle` usa `languageVersion = JavaLanguageVersion.of(25)`.
- Gradle Wrapper incluido en el proyecto.

## Como correrlo

Desde esta carpeta:

```bash
cd "/Users/diegoramirezmagana/Downloads/contexto marquet backend"
./gradlew bootRun
```

Cuando la aplicacion termine de arrancar, abre:

```text
http://localhost:8090/swagger-ui.html
```

Tambien puedes revisar el JSON de OpenAPI en:

```text
http://localhost:8090/v3/api-docs
```

## Endpoints principales

Productos:

- `GET /products/all`
- `GET /products/{id}`
- `GET /products/category/{categoryId}`
- `POST /products/save`
- `DELETE /products/delete/{id}`

Compras:

- `GET /purchases/all`
- `GET /purchases/client/{id}`
- `POST /purchases/save`

Prueba rapida:

```bash
curl http://localhost:8090/products/all
```

## Base de datos local

La copia usa H2 en memoria para desarrollo local. No necesitas tener PostgreSQL instalado para abrir Swagger.
Los datos de prueba se cargan automaticamente desde `src/main/resources/data.sql` cada vez que arranca la aplicacion.

Consola H2:

```text
http://localhost:8090/h2-console
```

Datos de conexion:

```text
JDBC URL: jdbc:h2:mem:marketdb
User: sa
Password:
```

## PostgreSQL

Si quieres usar PostgreSQL despues, cambia las propiedades de `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/market
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

Y ajusta:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

## Verificacion

Para compilar y correr pruebas:

```bash
./gradlew test
```
