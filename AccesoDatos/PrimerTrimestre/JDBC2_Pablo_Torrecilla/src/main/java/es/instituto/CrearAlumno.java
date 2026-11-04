package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import es.instituto.utils.Colors;

public class CrearAlumno {

  private static final String INSERT = "INSERT INTO alumnos (nombre, edad, ciclo) VALUES (?, ?, ?)";
  private static final String SQL = "SELECT * FROM alumnos WHERE id = ?";

  private CrearAlumno() {
  }

  public static void crear(BufferedReader reader) {
    try (Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement sqlPs = con.prepareStatement(SQL)) {

      System.out.print(Colors.BLUE + "Introduce el nombre del alumno: " + Colors.WHITE);
      String nombre;
      while (true) {
        nombre = reader.readLine();
        // Con String.matches("\\p{L}") indica que es una letra. El paréntesis extra indica que es opcional, permitiendo así nombres compuestos o simples
        if (nombre == null || nombre.isBlank() || !nombre.matches("\\p{L}+( \\p{L}+)*")) {
          System.out.print(Colors.RED + "Introduce un nombre válido: " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setString(1, nombre);

      System.out.print(Colors.BLUE + "Introduce la edad (>=18): " + Colors.WHITE);
      String edad;
      while (true) {
        edad = reader.readLine();
        if (edad == null || !edad.matches("\\d+") || Integer.parseInt(edad) < 18) {
          System.out.print(Colors.RED + "Introduce una edad válida (>18): " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setInt(2, Integer.parseInt(edad));

      System.out.print(Colors.BLUE + "Por último, introduce el ciclo del alumno (dam o daw): " + Colors.WHITE);
      String ciclo;
      while (true) {
        ciclo = reader.readLine();
        if (ciclo == null || ciclo.isBlank() || !ciclo.matches("\\p{L}+")
            || !List.of("DAM", "DAW").contains(ciclo.toUpperCase())) {
          System.out.print(Colors.RED + "Introduce un ciclo válido (dam o daw): " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setString(3, ciclo.toUpperCase());

      int update = ps.executeUpdate();

      long id = -1;
      ResultSet keys = ps.getGeneratedKeys();
      while (keys.next())
        id = keys.getLong(1);

      System.out.printf("%sSe %s insertado %d %s. El ID autogenerado es %d.%s%n",
          Colors.GREEN,
          update > 1 ? "han" : "ha",
          update,
          update > 1 ? "líneas" : "línea",
          id,
          Colors.RESET);

      sqlPs.setLong(1, id);
      try (ResultSet sqlRs = sqlPs.executeQuery()) {
        if (sqlRs.next()) {
          System.out.printf("%sID: %d | Nombre: %s | Edad: %d | Ciclo: %s%s%n",
              Colors.BLUE,
              sqlRs.getInt("ID"),
              sqlRs.getString("Nombre"),
              sqlRs.getInt("Edad"),
              sqlRs.getString("Ciclo"),
              Colors.RESET);
        }
      }

    } catch (IOException e) {
      System.err.printf("%sError en la creación del nuevo alumno. %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (SQLException ex) {
      System.err.printf("%sError de conexión con la base de datos. %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
