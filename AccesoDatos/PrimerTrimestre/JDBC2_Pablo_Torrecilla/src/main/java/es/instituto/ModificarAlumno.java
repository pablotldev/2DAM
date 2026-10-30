package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import es.instituto.utils.Colors;

public class ModificarAlumno {

  private static final String UPDATE = "UPDATE alumnos SET nombre = ?, edad = ?, ciclo = ? WHERE id = ?";

  public static void update() {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConexionInicial.getConnection();) {

      // Añadir algo del estilo "Datos actuales: <mostrar datos>" y en caso de actualizarlos mostrar solo lo que se ha cambiado como "nombre nuevo: <nombre>"

    } catch (SQLException e) {
      System.err.printf("%sError en ");
    } catch (IOException ex) {
      System.err.printf("%sError al modificar la tabla %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
