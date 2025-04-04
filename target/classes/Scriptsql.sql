-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS citas_campus;

USE citas_campus;

-- Tabla de especialidades
CREATE TABLE especialidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL
);

-- Tabla de médicos
CREATE TABLE medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    especialidad_id INT NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fin TIME NOT NULL,
    FOREIGN KEY (especialidad_id) REFERENCES especialidades(id)
);

-- Tabla de pacientes
CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    apellido VARCHAR(70) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(150),
    telefono VARCHAR(15),
    email VARCHAR(70)
);

-- Tabla de citas
CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    medico_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'pendiente',
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    FOREIGN KEY (medico_id) REFERENCES medicos(id)
);

-- Inserción de datos de ejemplo

-- Especialidades médicas
INSERT INTO especialidades (nombre) VALUES ('Medicina General');
INSERT INTO especialidades (nombre) VALUES ('Pediatría');
INSERT INTO especialidades (nombre) VALUES ('Dermatología');
INSERT INTO especialidades (nombre) VALUES ('endocrinología');

-- Médicos con sus especialidades
INSERT INTO medicos (nombre, especialidad_id, horario_inicio, horario_fin) 
VALUES ('Dr. Patrcio Rodriges', 1, '08:00:00', '16:00:00');

INSERT INTO medicos (nombre, especialidad_id, horario_inicio, horario_fin) 
VALUES ('Dra. María Paredes', 2, '09:00:00', '17:00:00');

INSERT INTO medicos (nombre, especialidad_id, horario_inicio, horario_fin) 
VALUES ('Dr. Carlos López', 3, '10:00:00', '18:00:00');

INSERT INTO medicos (nombre, especialidad_id, horario_inicio, horario_fin)
VALUES ('Dra. Ana Torres', 4, '11:00:00', '19:00:00');

-- Datos de pacientes
INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) 
VALUES ('Ana', 'Rodríguez', '1985-05-15', 'Calle Principal 123', '311554862', 'ana@email.com');

INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) 
VALUES ('Luis', 'Martínez', '1990-08-22', 'Avenida Central 456', '304589647', 'luis@email.com');

INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) 
VALUES ('Sofía', 'García', '1978-11-30', 'Boulevard Norte 789', '321458978', 'sofia@email.com');

INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email)
VALUES('Juan camilo', 'Arevalo', '1998-11-30', 'Avenida 54', '3204746369', 'juan@gmail.com');

-- Citas médicas programadas
INSERT INTO citas (paciente_id, medico_id, fecha_hora, estado) 
VALUES (1, 1, '2023-11-15 10:30:00', 'pendiente');

INSERT INTO citas (paciente_id, medico_id, fecha_hora, estado) 
VALUES (2, 2, '2023-11-16 14:00:00', 'confirmada');

INSERT INTO citas (paciente_id, medico_id, fecha_hora, estado) 
VALUES (3, 3, '2023-11-17 11:15:00', 'completada');