package es.instituto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListarAlumnos {
  private static final String SQL = "SELECT id, nombre, edad, ciclo FROM alumnos ORDER BY id";

  public static void selectAlumnos() {
    System.out.println("---- Ejercicio 2 ----");
    try (Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();) {
      while (rs.next()) {
        System.out.printf("ID: %d - Nombre: %s - Edad: %d - Ciclo: %s%n", rs.getInt(1), rs.getString(2), rs.getInt(3),
            rs.getString(4));
      }
    } catch (SQLException e) {
      System.out.printf("Error en el SELECT: %s%n", e.getMessage());
    }

  }

}
