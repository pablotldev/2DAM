package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.instituto.utils.Colors;

public class Main {

  private Main() {
  }

  private static void menu() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String salir;
      do {
        System.out.print(Colors.BLUE + "1.- Crear Alumno\n2.- Buscar Alumno\n3.- Modificar Alumno\n4.- Eliminar Alumno"
            + Colors.WHITE);
        salir = reader.readLine();
        if (salir == null || salir.isBlank() || !salir.matches("\\d+")) {
          System.out.println(Colors.RED + "Introduce un valor válido." + Colors.RESET);
          continue;
        }
        switch (Integer.parseInt(salir)) {
          case 1 -> CrearAlumno.crear(reader);
          case 2 -> BuscadorParam.buscar(reader);
          case 3 -> ModificarAlumno.update(reader);
          case 4 -> EliminarAlumno.delete(reader);
        }
      } while (!salir.toLowerCase().equals("salir"));
    } catch (IOException e) {
      System.err.printf("%sError en el menú: %s%s%n", Colors.RED, e.getMessage(), Colors.RESET);
    }

  }

  public static void main(String[] args) {
    menu();
    System.out.println(Colors.RED + "Ha salido del programa." + Colors.RESET);
  }
}
