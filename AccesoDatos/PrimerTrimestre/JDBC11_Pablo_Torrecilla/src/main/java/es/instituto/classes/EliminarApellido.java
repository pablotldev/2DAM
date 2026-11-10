package es.instituto.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import es.instituto.db.AlumnoDAO;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class EliminarApellido {

  private EliminarApellido() {
  }

  public static void eliminar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    String txt = Input.leerPalabra(reader, "Apellido o parte del apellido");

    try {
      int borrados = dao.deleteApellidos(txt);

      if (borrados == 0) {
        System.out.printf("%sNingún alumno tenía \"%s\" en los apellidos%n", Colors.YELLOW, txt);
        return;
      }
      System.out.printf("%sSe han eliminado %d alumno(s).%n", Colors.GREEN, borrados);
    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }

}
