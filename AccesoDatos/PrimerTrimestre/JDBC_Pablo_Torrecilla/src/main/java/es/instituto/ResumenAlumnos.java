package es.instituto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumenAlumnos {

  private static final String SQL = "SELECT ciclo, COUNT(*) AS Total_Alumnos, AVG(edad) AS Media_Edad FROM alumnos GROUP BY ciclo;";

  public static void resumen() {
    System.out.println("---- Ejercicio 5 ----");
    try (Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        System.out.printf("Ciclo: %s - Total de Alumnos: %d - Media de Edad: %.2f%n", rs.getString(1), rs.getInt(2),
            rs.getDouble(3));
      }

    } catch (SQLException e) {
      System.err.printf("Error de SQL: %s", e.getMessage());
    }
  }

}
