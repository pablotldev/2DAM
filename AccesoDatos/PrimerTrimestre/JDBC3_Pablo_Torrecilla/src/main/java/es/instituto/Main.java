package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import es.instituto.db.InitializeDB;
import es.instituto.demo.RestriccionDemo;
import es.instituto.demo.RollbackDemo;
import es.instituto.reports.InformeMovimientos;
import es.instituto.services.ResetDatos;
import es.instituto.services.TransferenciaService;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;

public class Main {

  private Main() {
  }

  private static void menu(BufferedReader reader) throws IOException {
    while (true) {
      System.out.println();
      System.out.println(Colors.BLUE + "==== Transacciones y errores JDBC ====");
      System.out.println(Colors.BLUE + "1.- Transferencia bancaria atómica");
      System.out.println("2.- Simular fallo y comprobar rollback");
      System.out.println("3.- Error de clave duplicada");
      System.out.println("4.- Informe de movimientos");
      System.out.println("5.- Restablecer datos de prueba");
      System.out.println("0.- Salir");
      int opt = ConsoleUtils.leerInt(reader, "Elige una opción: ");

      switch (opt) {
        case 1 -> TransferenciaService.ejecutar(reader);
        case 2 -> RollbackDemo.ejecutar();
        case 3 -> RestriccionDemo.ejecutar();
        case 4 -> InformeMovimientos.ejecutar();
        case 5 -> ResetDatos.ejecutar();
        case 0 -> {
          System.out.println(Colors.GREEN + "Fin del programa.");
          return;
        }
        default -> System.out.println(Colors.RED + "Opción no válida.");
      }
    }
  }

  public static void main(String[] args) {
    try {
      InitializeDB.initialize();
    } catch (IOException | SQLException e) {
      System.err.println(Colors.RED + "No se pudo inicializar la BD: " + e.getMessage());
      System.err.println("Revisa DB_SERVER en .env y los permisos del usuario.");
      return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      menu(reader);
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
    }
  }

}