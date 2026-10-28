package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumnosMayores {

  private static final String SQL = "SELECT * FROM alumnos WHERE ciclo = ? AND edad >= ? ORDER BY edad ASC;";

  public static void edadMinCiclo() {
    System.out.println("---- Ejercicio 4 ----");
    try (Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL);
        BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))) {
      System.out.print("Introduce el ciclo del alumnado: ");
      String ciclo = bR.readLine();
      ps.setString(1, ciclo);

      System.out.print("Introduce la edad mínima del alumnado: ");
      int edad = Integer.parseInt(bR.readLine());
      ps.setInt(2, edad);

      try (ResultSet rs = ps.executeQuery()) {
        boolean success = false;
        while (rs.next()) {
          success = true;
          System.out.printf("ID: %d - Nombre: %s - Edad: %d - Ciclo: %s%n", rs.getInt(1), rs.getString(2), rs.getInt(3),
              rs.getString(4));
        }

        if (!success)
          System.out.println("No hay alumnos que coincidan con esos filtros.");
      }
    } catch (SQLException e) {
      System.err.printf("Error de conexión en la base de datos: %s", e.getMessage());
    } catch (IOException ex) {
      System.err.printf("Error de entrada/salida: %s", ex.getMessage());
    }

  }

}
