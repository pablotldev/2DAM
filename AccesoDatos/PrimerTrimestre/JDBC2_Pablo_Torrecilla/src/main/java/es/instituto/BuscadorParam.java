package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.instituto.utils.Colors;

public class BuscadorParam {

  private static final String SQL = "SELECT * FROM alumnos WHERE ciclo = ? AND edad > ?;";

  private BuscadorParam() {
  }

  public static void buscar() {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL)) {

      System.out.print(Colors.BLUE + "Introduce el ciclo que quieras buscar (dam o daw): " + Colors.WHITE);
      String ciclo;
      while (true) {
        ciclo = bR.readLine();
        if (ciclo == null || ciclo.isBlank() || !ciclo.matches("\\p{L}+")
            || !List.of("DAW", "DAM").contains(ciclo.toUpperCase())) {
          System.out.print(Colors.RED + "Introduce un ciclo válido (dam o daw): " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setString(1, ciclo.toUpperCase());

      System.out.print(Colors.BLUE + "Introduce la edad mínima por la que quieras filtrar (>=18): " + Colors.WHITE);
      String edad;
      while (true) {
        edad = bR.readLine();
        if (edad == null || edad.isBlank() || !edad.matches("\\d+") || Integer.parseInt(edad) < 18) {
          System.out.print(Colors.RED + "Introduce una edad válida (>=18): " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setInt(2, Integer.parseInt(edad));
      boolean hayResultados = false;

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        hayResultados = true;
        System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
            Colors.GREEN,
            rs.getInt(1),
            rs.getString(2),
            rs.getInt(3),
            rs.getString(4),
            Colors.RESET);
      }

      if (!hayResultados) {
        System.out.println(Colors.RED + "No hay entradas que coincidan con el filtrado." + Colors.RESET);
      }

    } catch (IOException e) {
      System.err.printf("%sError en la creación del nuevo alumno. %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (SQLException ex) {
      System.err.printf("%sError de conexión con la base de datos. %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
