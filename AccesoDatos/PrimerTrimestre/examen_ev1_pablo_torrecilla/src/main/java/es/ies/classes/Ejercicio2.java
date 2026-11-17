package es.ies.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.ies.dao.AlumnoDAO;
import es.ies.dao.AlumnoDAOImpl;
import es.ies.db.ConnectionDB;
import es.ies.models.Solicitud;
import es.ies.utils.Colors;

public class Ejercicio2 {

  private Ejercicio2() {
  }

  private static void ejecutar() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection con = ConnectionDB.getConnection()) {

      String min = "", max = "";
      while (true) {
        System.out.println(Colors.BLUE);
        System.out.println("2.2.- Buscar alumnos en rango de edad.");
        System.out.print("Mínimo: " + Colors.WHITE);

        min = reader.readLine();
        if (!min.matches("\\d+")) {
          System.out.println(Colors.YELLOW + "Introduce una edad válida.");
          continue;
        }
        break;
      }
      while (true) {
        System.out.print(Colors.BLUE + "Máximo: " + Colors.WHITE);
        max = reader.readLine();
        if (!max.matches("\\d+")) {
          System.out.println(Colors.YELLOW + "Introduce una edad válida.");
          continue;
        }
        break;
      }

      AlumnoDAO dao = new AlumnoDAOImpl();
      dao.buscarAlumno(con, Integer.parseInt(min), Integer.parseInt(max));
      List<Solicitud> solicitudes = Ejercicio1.leerXML();

      System.out.println(Colors.BLUE);
      System.out.println("2.3.- Insertar alumnos del XML.");

      dao.insertarAlumnos(con, solicitudes);

    } catch (IOException ex) {
      System.err.println(Colors.RED + "Error de Entrada/Salida: " + ex.getMessage());
    } catch (SQLException ex) {
      System.err.println(Colors.RED + "Error de BD: " + ex.getMessage());
    }
  }

  public static void main() {
    ejecutar();
  }

}
