package es.instituto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumnosPorCiclo {

  private static final String FILTRO = "SELECT * FROM alumnos WHERE ciclo = ?";

  public static void buscarPorCiclo(String ciclo) {
    System.out.println("---- Ejercicio 3 ----");
    try (Connection con = ConexionInicial.getConnection(); PreparedStatement ps = con.prepareStatement(FILTRO)) {
      ps.setString(1, ciclo);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          System.out.printf("ID: %d - Nombre: %s - Edad: %d - Ciclo: %s%n", rs.getInt(1), rs.getString(2), rs.getInt(3),
              rs.getString(4));
        }
      }
    } catch (SQLException e) {
      System.err.printf("Error de filtrado: %s%n", e.getMessage());
    }

  }

}
