package es.instituto.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class InsertarAlumno {

  private InsertarAlumno() {
  }

  public static void crear(BufferedReader reader, AlumnoDAO dao) throws IOException {
    try {
      int nia = Input.leerNia(reader);

      if (dao.existeNia(nia)) {
        System.out.printf("%sYa existe un alumno con NIA %d.%n", Colors.RED, nia);
        return;
      }

      Alumno al = new Alumno(nia,
          Input.leerNombre(reader),
          Input.leerApellidos(reader),
          Input.leerGenero(reader),
          Input.leerFechaNacimiento(reader),
          Input.leerCiclo(reader),
          Input.leerCurso(reader),
          Input.leerGrupo(reader));

      dao.insert(al);
      System.out.println(Colors.GREEN + "Alumno insertado correctamente.");

    } catch (SQLIntegrityConstraintViolationException e) {
      System.err.println(Colors.RED + "Ya existe un alumno con ese NIA.");
    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }

}
