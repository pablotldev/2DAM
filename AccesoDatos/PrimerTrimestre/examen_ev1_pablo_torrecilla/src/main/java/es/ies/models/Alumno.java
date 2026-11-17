package es.ies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Alumno {
  private int id;
  private String nombre;
  private int edad;
  private String ciclo;
}
