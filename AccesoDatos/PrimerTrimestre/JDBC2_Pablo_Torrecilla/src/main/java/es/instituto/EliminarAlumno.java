package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.instituto.utils.Colors;

public class EliminarAlumno {

  private static final String SQL = "SELECT * FROM alumnos";
  private static final String SEARCH = "SELECT * FROM alumnos WHERE id = ?";
  private static final String DELETE = "DELETE FROM alumnos WHERE id = ?";

  private EliminarAlumno() {
  }

  public static void delete(BufferedReader reader) {

    try (Connection con = ConexionInicial.getConnection();
        PreparedStatement sqlPs = con.prepareStatement(SQL);
        PreparedStatement searchPs = con.prepareStatement(SEARCH)) {

      try (ResultSet sqlRs = sqlPs.executeQuery()) {
        System.out.println("------ Listado de todos los alumnos ------");
        while (sqlRs.next()) {
          System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
              Colors.BLUE,
              sqlRs.getInt("ID"),
              sqlRs.getString("Nombre"),
              sqlRs.getInt("Edad"),
              sqlRs.getString("Ciclo"),
              Colors.RESET);
        }
      }
      System.out.print(Colors.BLUE + "Introduce el ID del alumno que quieres eliminar: " + Colors.WHITE);
      String id;
      while (true) {
        id = reader.readLine();
        if (id == null || id.isBlank() || !id.matches("\\d+")) {
          System.out.print(Colors.RED + "Introduce un ID válido: " + Colors.WHITE);
          continue;
        }
        break;
      }
      searchPs.setInt(1, Integer.parseInt(id));
      try (ResultSet searchRs = searchPs.executeQuery()) {
        if (!searchRs.next()) {
          System.out.printf("%sNo se ha encontrado ningún alumno con el id %s%s%n",
              Colors.RED, id, Colors.RESET);
          return;
        }

        System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
            Colors.BLUE,
            searchRs.getInt("Id"),
            searchRs.getString("Nombre"),
            searchRs.getInt("Edad"),
            searchRs.getString("Ciclo"),
            Colors.RESET);
      }

      System.out.printf("%sVas a eliminar al alumno con la ID %s, ¿estás seguro? (s/N): %s",
          Colors.YELLOW,
          id,
          Colors.WHITE);

      String confirm;
      while (true) {
        confirm = reader.readLine();
        if (confirm == null) {
          confirm = "n";
          break;
        }
        confirm = confirm.trim().toLowerCase();
        if (confirm.isEmpty()) {
          confirm = "n";
          break;
        }
        if (confirm.equals("n") || confirm.equals("s")) {
          break;
        }
        System.out.print(Colors.RED + "Introduce una opción válida (s/N): " + Colors.WHITE);
      }

      try (PreparedStatement delete = con.prepareStatement(DELETE)) {
        if (confirm.equals("s")) {
          delete.setInt(1, Integer.parseInt(id));
          int borrado = delete.executeUpdate();
          if (borrado == 1) {
            System.out.printf("%sSe ha eliminado el alumno con la ID: %s.%s%n",
                Colors.GREEN, id, Colors.RESET);
            return;
          }
        }
      }

      System.out.println(Colors.GREEN + "No se ha eliminado ningún alumno" + Colors.RESET);

    } catch (SQLException e) {
      System.err.printf("%sError en la base de datos: %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (IOException ex) {
      System.err.printf("%sError al modificar la tabla %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    } catch (NumberFormatException en) {
      System.err.printf("%sError de parseado: %s%s%n", Colors.RED, en.getMessage(), Colors.RESET);
    }

  }

}
