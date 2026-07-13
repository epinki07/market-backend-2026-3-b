# Correcciones tecnicas

## Error original de MapStruct

El error mostrado por IntelliJ era:

```text
Cycle detected while evaluating inherited configurations
```

La causa estaba en `CategoryMapper`. El metodo inverso recibia una entidad `Categoria` y devolvia otra entidad `Categoria`:

```java
Categoria toCategoria(Categoria categoria);
```

Con `@InheritInverseConfiguration`, MapStruct esperaba invertir el metodo:

```java
Category toCategory(Categoria categoria);
```

Por eso el metodo inverso correcto debe recibir el modelo de dominio:

```java
Categoria toCategoria(Category category);
```

## Estado de producto

`Producto.estado` estaba declarado como `String`, pero `ProductoCrudRepository` usaba:

```java
findByCantidadStockLessThanAndEstado(int cantidad, boolean estado)
```

Se cambio `estado` a `boolean` para que la entidad, el mapper y la query derivada usen el mismo tipo.

## Swagger

Se agrego:

```gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.17'
```

Tambien se usa Spring Boot `3.5.12`, porque la matriz oficial de springdoc indica que Spring Boot `3.5.x` corresponde a springdoc `2.8.x`.

La documentacion oficial de springdoc indica que Swagger UI queda disponible en:

```text
http://server:port/context-path/swagger-ui.html
```

En este proyecto el puerto es `8090`, asi que la URL es:

```text
http://localhost:8090/swagger-ui.html
```

Tambien se configuro:

```properties
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```

Esto evita el error de arranque causado por el patron interno de Swagger UI:

```text
/swagger-ui/**/*swagger-initializer.js
```

## Base de datos local

Para que Swagger pueda abrir sin configurar PostgreSQL, se agrego H2 en memoria y una configuracion local en `application.properties`.

Tambien se agrego `src/main/resources/data.sql` para cargar categorias, productos, un cliente y una compra de prueba al arrancar.
