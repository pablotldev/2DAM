package es.instituto.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class ModificarAlumno {

  private ModificarAlumno() {
  }

  public static void modificar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    try {
      int nia = Input.leerNia(reader);
      Optional<Alumno> encontrado = dao.buscarNia(nia);

      if (encontrado.isEmpty()) {
        System.out.printf("%sNo existe ningún alumno con NIA: %d%n", Colors.RED, nia);
        return;
      }

      System.out.printf("%sNombre Actual: %s%s%n", Colors.BLUE, Colors.PURPLE, encontrado.get().nombre());

      String nombre = Input.leerNombre(reader);

      if (dao.updateNombre(nia, nombre)) {
        System.out.println(Colors.GREEN + "Nombre actualizado correctamente.");
        return;
      }
      System.out.println(Colors.RED + "No se pudo actualizar el alumno.");

    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }

}
