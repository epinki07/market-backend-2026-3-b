INSERT INTO categorias (descripcion, estado) VALUES
    ('Frutas y verduras', true),
    ('Lacteos', true),
    ('Panaderia', true);

INSERT INTO productos (nombre, id_categoria, codigo_barras, precio_venta, cantidad_stock, estado) VALUES
    ('Manzana roja', 1, '7501000000011', 18, 60, true),
    ('Platano', 1, '7501000000028', 14, 45, true),
    ('Leche entera', 2, '7501000000035', 28, 30, true),
    ('Pan integral', 3, '7501000000042', 35, 20, true);

INSERT INTO clientes (id, nombre, apellidos, celular, direccion, correo_electronico) VALUES
    ('CLI001', 'Diego', 'Ramirez', 9991234567, 'Merida, Yucatan', 'diego@example.com');

INSERT INTO compras (id_cliente, fecha, medio_pago, comentario, estado) VALUES
    ('CLI001', CURRENT_TIMESTAMP, 'E', 'Compra de prueba', 'A');

INSERT INTO compras_productos (id_compra, id_producto, cantidad, total, estado) VALUES
    (1, 1, 2, 36.0, true),
    (1, 3, 1, 28.0, true);
