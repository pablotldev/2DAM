package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.instituto.utils.Colors;

public class Main {

  private Main() {
  }

  private static void menu() {
    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))) {
      String salir;
      do {
        System.out.print(Colors.BLUE + "1.- Crear Alumno\n2.- Buscar Alumno\n3.- Modificar Alumno\n4.- Eliminar Alumno"
            + Colors.WHITE);
        salir = bR.readLine();
        if (salir == null || salir.isBlank() || !salir.matches("\\d+")) {
          System.out.println(Colors.RED + "Introduce un valor válido." + Colors.RESET);
          continue;
        }
        switch (Integer.parseInt(salir)) {
          case 1 -> CrearAlumno.crear();
          case 2 -> BuscadorParam.buscar();
          case 3 -> ModificarAlumno.update();
          case 4 -> EliminarAlumno.delete();
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
