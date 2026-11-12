CREATE DATABASE IF NOT EXISTS tienda_simulacro;

USE tienda_simulacro;

CREATE TABLE IF NOT EXISTS productos (
  id INT PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL
);

INSERT INTO productos (id,nombre,precio,stock) VALUES
(1,'Teclado',25.00,4),(2,'Raton',12.50,0),
(3,'Monitor',175.00,2)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),
precio=VALUES(precio),stock=VALUES(stock);