package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.instituto.utils.Colors;

public class EliminarAlumno {

  private static final String SQL = "SELECT * FROM alumnos;";
  private static final String SEARCH = "SELECT * FROM alumnos WHERE id = ?;";
  private static final String DELETE = "DELETE FROM alumnos WHERE id = ?";

  private EliminarAlumno() {
  }

  public static void delete() {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConexionInicial.getConnection();
        PreparedStatement sql = con.prepareStatement(SQL);
        PreparedStatement search = con.prepareStatement(SEARCH);
        PreparedStatement delete = con.prepareStatement(DELETE);) {

      try (ResultSet sql_rs = sql.executeQuery()) {
        System.out.println("------ Listado de todos los alumnos ------");
        while (sql_rs.next()) {
          System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
              Colors.BLUE,
              sql_rs.getInt(1),
              sql_rs.getString(2),
              sql_rs.getInt(3),
              sql_rs.getString(4),
              Colors.RESET);
        }
      }
      System.out.print(Colors.BLUE + "Introduce el ID del alumno que quieres eliminar: " + Colors.WHITE);
      String id;
      while (true) {
        id = bR.readLine();
        if (id == null || id.isBlank() || !id.matches("\\d+")) {
          System.out.print(Colors.RED + "Introduce un ID válido: " + Colors.WHITE);
          continue;
        }
        break;
      }
      search.setInt(1, Integer.parseInt(id));
      try (ResultSet search_rs = search.executeQuery()) {
        boolean encontrado = false;
        if (search_rs.next()) {
          encontrado = true;
          System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
              Colors.BLUE,
              search_rs.getInt(1),
              search_rs.getString(2),
              search_rs.getInt(3),
              search_rs.getString(4),
              Colors.RESET);
        }

        if (!encontrado) {
          System.out.printf("%sNo se ha encontrado ningún alumno con el id %s%s%n",
              Colors.RED, id, Colors.RESET);
          return;
        }
      }

      System.out.printf("%sVas a eliminar al alumno con la ID %s, ¿estás seguro? (s/N): %s",
          Colors.YELLOW,
          id,
          Colors.WHITE);

      String confirm;
      while (true) {
        confirm = bR.readLine();
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
        System.out.print("Introduce una opción válida (s/N): ");
      }

      if (confirm.equals("s")) {
        delete.setInt(1, Integer.parseInt(id));
        delete.executeUpdate();
        System.out.printf("%sSe ha eliminado el alumno con la ID: %s.%s%n",
            Colors.GREEN, id, Colors.RESET);
        return;
      }
      System.out.println(Colors.GREEN + "No se ha eliminado ningún alumno" + Colors.RESET);

    } catch (SQLException e) {
      System.err.printf("%sError en la base de datos: %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (IOException ex) {
      System.err.printf("%sError al modificar la tabla %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
