package es.instituto.models;

import java.time.LocalDate;

public record Alumno(
    int nia,
    String nombre,
    String apellidos,
    String genero,
    LocalDate fechaNacimiento,
    String ciclo,
    String curso,
    char grupo) {
}
