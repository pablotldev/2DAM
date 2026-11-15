package es.instituto.models;

import es.instituto.utils.Colors;

public record Alumno(int id, String nombre, int edad, String ciclo) {

  @Override
  public String toString() {
    return String.format("%s  ID: %d | Nombre: %s | Edad: %d | Ciclo: %s", Colors.GREEN, id, nombre, edad, ciclo);
  }

}
