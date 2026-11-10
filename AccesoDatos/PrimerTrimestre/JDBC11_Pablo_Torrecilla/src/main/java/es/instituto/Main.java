package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import es.instituto.classes.InsertarAlumno;
import es.instituto.db.AlumnoDAO;
import es.instituto.db.InitializeDB;
import es.instituto.utils.Colors;

public class Main {

  private static final int END = 0;

  private static void initialize() {
    try {
      InitializeDB.initialize();
    } catch (SQLException | IOException e) {
      System.err.printf("%sError de carga del archivo /resources/db/schema.sql: %s", Colors.RED, e.getMessage());
      System.exit(1);
    }
  }

  private static void menu() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String opt = null;
      System.out.println(Colors.PURPLE + "---------- MENÚ ----------");
      System.out.println(Colors.BLUE + "1.- Insertar nuevo alumno");
      System.out.println("2.- Mostrar alumnos");
      System.out.println("3.- Guardar en fichero");
      System.out.println("4.- Leer fichero");
      System.out.println("5.- Modificar alumno");
      System.out.println("6.- Eliminar alumno");
      System.out.println("7.- Eliminar apellidos");
      System.out.println("8.- Guardar XML/JSON");
      System.out.println("9.- Leer XML/JSON");
      System.out.println("0.- Salir");
      System.out.print("Introduce una opción: " + Colors.WHITE);

      do {
        opt = reader.readLine();
        if (opt == null || opt.isBlank() || !opt.matches("\\d")) {
          System.err.print(Colors.RED + "Introduce una opción válida (0-9): " + Colors.WHITE);
          continue;
        }
        break;
      } while (true);

      switch (Integer.parseInt(opt)) {
        case 1 -> AlumnoDAO.ins;
      }

    } catch (IOException e) {
      System.err.printf("%sError de Entrada/Salida: %s", Colors.RED, e.getMessage());
      System.exit(1);
    }
  }

  public static void main(String[] args) {

    initialize();
    menu();

  }

}
