CREATE DATABASE IF NOT EXISTS instituto;
USE instituto;
CREATE TABLE IF NOT EXISTS alumnos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,
  edad INT NOT NULL,
  ciclo VARCHAR(10) NOT NULL
);
INSERT INTO alumnos(nombre,edad,ciclo) VALUES
('Ana',20,'DAM'),('Luis',22,'DAW'),('Marta',19,'DAM');