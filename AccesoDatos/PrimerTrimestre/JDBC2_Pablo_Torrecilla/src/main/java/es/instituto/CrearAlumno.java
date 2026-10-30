package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import es.instituto.utils.Colors;

public class CrearAlumno {

  private static final String INSERT = "INSERT INTO alumnos (nombre, edad, ciclo) VALUES (?, ?, ?);";

  private CrearAlumno() {
  }

  public static void crear() {
    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConexionInicial.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

      System.out.print(Colors.BLUE + "Introduce el nombre del alumno: " + Colors.WHITE);
      String nombre;
      while (true) {
        nombre = bR.readLine();
        // Con String.matches("\\p{L}") indica que es una letra. El paréntesis extra indica que es opcional, permitiendo así nombres compuestos o simples
        if (nombre == null || nombre.isBlank() || !nombre.matches("\\p{L}+( \\p{L}+)*")) {
          System.out.print(Colors.BLUE + "Introduce un nombre válido: " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setString(1, nombre);

      System.out.print(Colors.BLUE + "Introduce la edad: " + Colors.WHITE);
      String edad;
      while (true) {
        edad = bR.readLine();
        if (edad == null || !edad.matches("\\d+")) {
          System.out.print(Colors.RED + "Introduce una edad válida: " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setInt(2, Integer.parseInt(edad));

      System.out.print(Colors.BLUE + "Por último, introduce el ciclo del alumno (dam o daw): " + Colors.WHITE);
      String ciclo;
      while (true) {
        ciclo = bR.readLine();
        if (ciclo == null || ciclo.isBlank() || !ciclo.matches("\\p{L}+")
            || !List.of("dam", "daw").contains(ciclo.toLowerCase())) {
          System.out.print(Colors.RED + "Introduce un ciclo válido (dam o daw): " + Colors.WHITE);
          continue;
        }
        break;
      }
      ps.setString(3, ciclo);

      int update = ps.executeUpdate();

      System.out.printf(Colors.GREEN + "Se %s insertado %d %s. El ID autogenerado es %d.%n",
          update > 1 ? "han" : "ha", update, update > 1 ? "líneas" : "línea", ps.getGeneratedKeys());

    } catch (IOException e) {
      System.err.printf("%sError en la creación del nuevo alumno. %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    } catch (SQLException ex) {
      System.err.printf("%sError de conexión con la base de datos. %s%s%n", Colors.RED, ex.getMessage(), Colors.RESET);
    }

  }

}
