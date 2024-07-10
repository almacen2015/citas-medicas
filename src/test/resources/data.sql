CREATE TABLE clientes
(
    id               INT PRIMARY KEY,
    nombre           VARCHAR(50),
    apellido_paterno VARCHAR(50),
    apellido_materno VARCHAR(50),
    numero_documento VARCHAR(20),
    fecha_nacimiento DATE,
    email            VARCHAR(50),
    telefono         VARCHAR(20),
    sexo             CHAR(1)
);

INSERT INTO clientes
(id,
 nombre,
 apellido_paterno,
 apellido_materno,
 numero_documento,
 fecha_nacimiento,
 email,
 telefono,
 sexo)
VALUES
    (1, 'Juan', 'Perez', 'Gomez', '12345678', '1990-01-01', 'juan@gmail.com', '12345678', 'M'),
    (2, 'Maria', 'Gomez', 'Perez', '87654321', '1995-01-01', 'maria@gmail.com', '87654321', 'F');

CREATE TABLE areas
(
    id   INT PRIMARY KEY,
    nombre VARCHAR(50),
    estado BOOLEAN
);

INSERT INTO areas
    (id, nombre, estado) VALUES (1, 'Medicina General', TRUE),
    (2, 'Traumatologia', TRUE),
    (3, 'Ventas', TRUE);

