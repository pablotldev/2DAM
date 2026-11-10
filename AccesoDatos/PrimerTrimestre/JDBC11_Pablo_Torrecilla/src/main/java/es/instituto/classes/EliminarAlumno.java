package es.instituto.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class EliminarAlumno {

  private EliminarAlumno() {
  }

  public static void eliminar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    try {
      int nia = Input.leerNia(reader);
      Optional<Alumno> encontrado = dao.buscarNia(nia);

      if (encontrado.isEmpty()) {
        System.out.printf("%sNo existe ningún alumno con NIA: %d.%n", Colors.RED, nia);
        return;
      }

      Alumno alumno = encontrado.get();
      System.out.printf("%sAlumno: %d | %s %s%n", Colors.PURPLE, alumno.nia(), alumno.nombre(), alumno.apellidos());

      if (!Input.confirm(reader, "¿Eliminar este alumno?")) {
        System.out.println(Colors.YELLOW + "Eliminación cancelada.");
        return;
      }

      if (dao.delete(nia)) {
        System.out.println(Colors.GREEN + "Alumno eliminado correctamente");
        return;
      }
      System.out.println(Colors.RED + "No se pudo eliminar el alumno.");

    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }

}
