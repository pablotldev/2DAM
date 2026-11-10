package es.instituto.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import es.instituto.db.AlumnoDAO;
import es.instituto.utils.Input;

public class CSVAlumnos {

  private static final Path DEFECTO = Path.of("data", "alumnos.csv");
  private static final String CABECERA = "NIA;Nombre;Apellidos;Genero;Fecha_nacimiento;Ciclo;Curso;Grupo";
  private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
      .withResolverStyle(ResolverStyle.STRICT);

  private CSVAlumnos() {
  }

  public static void guardar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    Path ruta = Input.leerRuta();
  }

}
