package semana3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PracticaFinal {

  private final static Path FILE = Path.of("src/calificaciones.csv");

  public static void crearFichero() {

    try {

      if (!Files.exists(FILE.getParent())) {
        Files.createDirectories(FILE.getParent());
        System.out.printf("Directorio %s creado.%n", FILE.getParent());
      } else {
        System.out.printf("El directorio %s ya existe.%n", FILE.getParent());
      }

      if (!Files.exists(FILE)) {

        Files.createFile(FILE);
        Files.writeString(FILE, "id;nombre;modulo;nota");
        System.out.printf("El fichero '%s' se ha creado correctamente %n", FILE.getFileName());

      } else {

        System.out.printf("El fichero %s ya existe %n", FILE.getFileName());

      }

    } catch (IOException e) {
      System.out.print("Error de entrada/salida");
      System.err.println(e.getMessage());
    }

  }

  public static void añadirAlFichero(BufferedReader bR) {

    try (BufferedWriter bW = new BufferedWriter(new FileWriter(FILE.toFile(), true))) {
      boolean flag = false;

      do {
        System.out.print("Introduce un nombre: ");
        String name = bR.readLine();

        if (name.isBlank()) {

          System.out.print("Nombre no válido.");
          continue;

        }

        System.out.print("Introduce un módulo: ");
        String module = bR.readLine();

        if (module.isBlank()) {

          System.out.print("Módulo no válido.");
          continue;

        }

        System.out.printf("Introduce una nota para %s: ", name);
        double mark = Double.parseDouble(bR.readLine());

        if (mark < 0 || mark > 10) {

          System.out.print("Nota no válida.");
          continue;

        }

        try (Stream<String> line = Files.lines(FILE)) {

          int id = (int) line.count();
          bW.newLine();
          bW.write(String.format("%d;%s;%s;%.2f", id, name, module, mark));

        }

        flag = true;

      } while (flag == false);

    } catch (IOException e) {

      System.out.println("Error de entrada/salida");
      System.err.println(e.getMessage());

    }

  }

  public static void listarTodo() {

    try (BufferedReader bR = new BufferedReader(new FileReader(FILE.toFile()))) {

      String str; 
      bR.readLine();
      while ((str = bR.readLine()) != null) {

        if (str.isBlank()) {
          continue;
        }

        String[] palabras = str.split(";");

        String nom = palabras[1];
        String course = palabras[2];
        Double mark = Double.parseDouble(palabras[3].replace(",", "."));

        System.out.printf("Nombre: %s, Curso: %s, Nota: %.2f%n", nom, course, mark);

      }

    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
    }

  }

  public static void listarAprobados() {

    try (BufferedReader bR = new BufferedReader(new FileReader(FILE.toFile()))) {

      String str = bR.readLine();
      while ((str = bR.readLine()) != null) {

        if (str.isBlank()) {
          continue;
        }

        String[] palabras = str.split(";");

        Double mark = Double.parseDouble(palabras[3].replace(",", "."));
        if (mark < 5) {
          continue;
        }
        String nom = palabras[1];
        String course = palabras[2];
        System.out.printf("Nombre: %s, Curso: %s, Nota: %.2f%n", nom, course, mark);

      }

    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
    }

  }

  public static void media() {

    try (BufferedReader bR = new BufferedReader(new FileReader(FILE.toFile()))) {

      double avg = 0, total = 0;
      int iterator = 0;
      String str = bR.readLine();
      while ((str = bR.readLine()) != null) {
        iterator++;

        if (str.isBlank()) {
          continue;
        }

        String[] palabras = str.split(";");

        Double mark = Double.parseDouble(palabras[3].replace(",", "."));
        total += mark;

      }
      avg = total / iterator;
      System.out.printf("La media es de %.2f", avg);

    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
    }
  }

  public static void buscarAlumno(BufferedReader bR) {

    try (BufferedReader bRS = new BufferedReader(new FileReader(FILE.toFile()))) {

      System.out.print("Dime el nombre del alumno del que quieres la información: ");
      String name = bR.readLine();

      String str = bRS.readLine();
      while ((str = bRS.readLine()) != null) {

        if (str.isBlank()) {
          continue;
        }

        String[] palabras = str.split(";");

        String nom = palabras[1];
        if (!name.toLowerCase().equals(nom)) {
          System.out.println("El alumno no existe");
          continue;
        }

        String course = palabras[2];
        Double mark = Double.parseDouble(palabras[3].replace(",", "."));
        System.out.printf("Nombre: %s, Curso: %s, Nota: %.2f%n", nom, course, mark);

      }

    } catch (IOException e) {
      System.out.println("Error de entrada/salida");
      System.err.println(e.getMessage());
    }
  }

  public static void main(String[] args) {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));) {

      int val = 0;

      do {

        System.out.println(
            "\n1. Crear fichero 'calificaciones.csv'.\n2. Añadir al fichero.\n3. Listar calificaciones.\n4. Mostrar aprobados.\n5. Calcular nota media.\n6. Buscar alumno.\n0. Salir");
        try {
          val = Integer.parseInt(bR.readLine());
        } catch (NumberFormatException e) {
          System.err.println("Error: introduce un número");
          e.getMessage();
          val = -1;
        }

        switch (val) {
          case 1:
            crearFichero();
            break;

          case 2:
            añadirAlFichero(bR);
            break;

          case 3:
            listarTodo();
            break;

          case 4:
            listarAprobados();
            break;

          case 5:
            media();
            break;

          case 6:
            buscarAlumno(bR);
            break;

          case 0:
            val = 0;
            System.out.println("Ha cerrado el programa.");
            break;

          default:
            System.out.println("Introduce un valor propuesto.");
            break;
        }

      } while (val != 0);

    } catch (IOException e) {
      System.out.println("Error de entrada/salida");
      System.err.println(e.getMessage());
    }
  }
}
