package es.instituto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
  private int id;
  private String nombre;
  private double precio;
  private int stock;
}
