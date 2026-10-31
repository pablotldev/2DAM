package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.instituto.utils.Colors;

public class ModificarAlumno {

  private static final String SQL = "SELECT * FROM alumnos;";
  private static final String SEARCH = "SELECT * FROM alumnos WHERE id = ?;";
  private static final String UPDATE = "UPDATE alumnos SET ciclo = ? WHERE id = ?";

  private ModificarAlumno() {
  }

  public static void update() {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConexionInicial.getConnection();
        PreparedStatement sql = con.prepareStatement(SQL);
        PreparedStatement search = con.prepareStatement(SEARCH);
        PreparedStatement update = con.prepareStatement(UPDATE);) {

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
      System.out.print(Colors.BLUE + "Introduce el ID del alumno al que se le cambiará el ciclo: " + Colors.WHITE);
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
        String ciclo_og = null;
        if (search_rs.next()) {
          encontrado = true;
          ciclo_og = search_rs.getString(4);
          System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
              Colors.BLUE,
              search_rs.getInt(1),
              search_rs.getString(2),
              search_rs.getInt(3),
              ciclo_og,
              Colors.RESET);
        }

        if (!encontrado) {
          System.out.printf("%sNo se ha encontrado ningún alumno con el id %s%s%n",
              Colors.RED, id, Colors.RESET);
          return;
        }

        String ciclo = "DAW".equals(ciclo_og) ? ciclo_og : "DAM";
        update.setString(1, ciclo);
        update.executeUpdate();
        System.out.printf("%sSe ha cambiado el ciclo %s por %s correctamente.%s%n",
            Colors.GREEN,
            ciclo_og,
            ciclo,
            Colors.RESET);

      }

      update.executeUpdate();

    } catch (SQLException e) {
      System.err.printf("%sError en la base de datos: %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (IOException ex) {
      System.err.printf("%sError al modificar la tabla %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
