INSERT INTO AREAS (id, nombre, estado) VALUES (1, 'Medicina', true);
INSERT INTO AREAS (id, nombre, estado) VALUES (2, 'Odontologia', true);

INSERT INTO CLIENTES (id, nombre, apellido_paterno, apellido_materno, numero_documento, fecha_nacimiento, sexo,
                      telefono, email)
VALUES (1, 'Juan', 'Pérez', 'Gómez', '12345678', '1985-05-10', 'M', '5551234', 'juan.perez@example.com');

INSERT INTO CLIENTES (id, nombre, apellido_paterno, apellido_materno, numero_documento, fecha_nacimiento, sexo,
                      telefono, email)
VALUES (2, 'María', 'García', 'Rodríguez', '87654321', '1990-11-20', 'F', '5554321', 'maria.garcia@example.com');

INSERT INTO CITAS (cliente_id, area_id, titulo, fecha_inicio, fecha_fin, estado)
VALUES (1, 1, 'Consulta General', '2024-11-01 09:00:00', '2024-11-01 10:00:00', 'A');

INSERT INTO CITAS (cliente_id, area_id, titulo, fecha_inicio, fecha_fin, estado)
VALUES (2, 2, 'Revisión de Proyecto', '2024-11-02 11:00:00', '2024-11-02 12:30:00', 'I');