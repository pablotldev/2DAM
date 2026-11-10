package es.instituto;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import es.instituto.classes.EliminarAlumno;
import es.instituto.classes.EliminarApellido;
import es.instituto.classes.InsertarAlumno;
import es.instituto.classes.ModificarAlumno;
import es.instituto.classes.MostrarAlumnos;
import es.instituto.db.AlumnoDAO;
import es.instituto.db.InitializeDB;
import es.instituto.files.CSVAlumnos;
import es.instituto.files.XMLAlumnos;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class Main {

  private static final int END = 0;

  private Main() {
  }

  private static void initialize() {
    try {
      InitializeDB.initialize();
    } catch (SQLException | IOException e) {
      System.err.printf("%sError de carga del archivo /resources/db/schema.sql: %s", Colors.RED, e.getMessage());
      System.exit(1);
    }
  }

  private static void menu(BufferedReader reader, AlumnoDAO dao) throws IOException {
    while (true) {
      System.out.println();
      System.out.println(Colors.PURPLE + "---------- MENÚ ----------");
      System.out.println(Colors.BLUE + "1.- Insertar nuevo alumno");
      System.out.println("2.- Mostrar alumnos");
      System.out.println("3.- Guardar en fichero CSV");
      System.out.println("4.- Leer fichero CSV");
      System.out.println("5.- Modificar alumno");
      System.out.println("6.- Eliminar alumno");
      System.out.println("7.- Eliminar apellidos");
      System.out.println("8.- Guardar XML");
      System.out.println("9.- Leer XML");
      System.out.println("0.- Salir");

      int opt = Input.leerOpcion(reader, 0, 9);

      if (opt == END)
        return;

      switch (opt) {
        case 1 -> InsertarAlumno.crear(reader, dao);
        case 2 -> MostrarAlumnos.mostrar(dao);
        case 3 -> CSVAlumnos.guardar(reader, dao);
        case 4 -> CSVAlumnos.leer(reader, dao);
        case 5 -> ModificarAlumno.modificar(reader, dao);
        case 6 -> EliminarAlumno.eliminar(reader, dao);
        case 7 -> EliminarApellido.eliminar(reader, dao);
        case 8 -> XMLAlumnos.guardar(reader, dao);
        case 9 -> XMLAlumnos.leer(reader, dao);
      }

    }
  }

  public static void main(String[] args) {
    initialize();

    AlumnoDAO dao = new AlumnoDAO();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      menu(reader, dao);
      System.out.println(Colors.YELLOW + "Programa finalizado.");
    } catch (EOFException e) {
      System.err.println(Colors.YELLOW + "Entrada finalizada. Saliendo...");
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de E/S: " + e.getMessage());
    }

  }

}
