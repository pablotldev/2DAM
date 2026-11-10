package es.instituto.classes;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;

public class MostrarAlumnos {

  private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu");

  private MostrarAlumnos() {
  }

  public static void mostrar(AlumnoDAO dao) {
    try {
      List<Alumno> alumnos = dao.listar();

      if (alumnos.isEmpty()) {
        System.out.println(Colors.YELLOW + "No hay alumnos en la base de datos.");
        return;
      }

      System.out.printf("%s%-5s %-15s %-25s %-10s %-12s %-7s %-6s %-6s%s%n", Colors.PURPLE, "NIA", "Nombre",
          "Apellidos",
          "Género", "Nacimiento", "Ciclo", "Curso", "Grupo", Colors.RESET);
      System.out.println(Colors.PURPLE + "-".repeat(100) + Colors.RESET);

      for (Alumno a : alumnos) {
        System.out.printf("%-5d %-15s %-25s %-10s %-12s %-7s %-6s %-6s%n",
            a.nia(),
            a.nombre(),
            a.apellidos(),
            a.genero(),
            FECHA.format(a.fechaNacimiento()),
            a.ciclo(),
            a.curso(),
            a.grupo());
      }

      System.out.printf("%sTotal: %d alumno(s).%s%n", Colors.GREEN, alumnos.size(), Colors.RESET);
    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }
}
