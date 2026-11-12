package es.instituto.pregunta1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import es.instituto.utils.Colors;

public class Main {

  private static final Path FILE_CSV = Path.of("src/main/resources/notas.csv");
  private static final Path FILE_TXT = Path.of("src/main/resources/aprobados.txt");

  private static void leerCSV() {
    int valid = 0, pass = 0;
    double avg = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_CSV.toFile()))) {

      String line;
      br.readLine();

      while ((line = br.readLine()) != null) {
        if (line.isBlank())
          continue;

        String[] words = line.split(";");
        valid++;

        if (!words[0].matches("\\d+")) {
          System.err.println(Colors.RED + "El ID " + Colors.PURPLE + words[0] + Colors.RED
              + " no tiene un formato válido, tiene que ser un " + Colors.PURPLE + "ENTERO");
          continue;
        }
        int id = Integer.parseInt(words[0]);
        String name = words[1];

        if (!words[2].matches("\\d+.[\\d+]*")) {
          System.err.println(Colors.RED + "La nota " + Colors.PURPLE + words[2] + Colors.RED + " debe ser un "
              + Colors.PURPLE + "DOUBLE");
          continue;
        }
        double mark = Double.parseDouble(words[2]);
        avg += mark;

        if (mark < 0 || mark > 10) {
          System.err.println(Colors.RED + "La nota " + Colors.PURPLE + words[2] + Colors.RED + " está fuera del rango "
              + Colors.PURPLE + " 0 - 10");
          continue;
        }
        if (mark >= 5) {
          crearAprobadosTXT(id, name, mark);
          pass++;
        }
      }
    } catch (IOException e) {
      System.err.printf("%sError de lectura del fichero %s: %s%n", Colors.RED, FILE_CSV.toAbsolutePath(),
          e.getMessage());
    }
    avg /= valid;
System.out.printf("%s%d%s registros válidos, %s%d%s aprobados y media %s%.2f%n", Colors.PURPLE, valid, Colors.BLUE, Colors.GREEN, pass, Colors.BLUE, Colors.PURPLE, avg);
  }

  private static void crearAprobadosTXT(int id, String name, double mark) {
    try {
      if (!Files.exists(FILE_CSV)) {
        Files.createFile(FILE_TXT);
      }
    } catch (IOException e) {
      System.err.printf("%sError de creación del fichero %s: %s%n", Colors.RED, FILE_TXT.toAbsolutePath(),
          e.getMessage());
      System.exit(1);
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_TXT.toFile(), true));
        Stream<String> line = Files.lines(FILE_TXT)) {

      if ((int) line.count() != 0) {
        bw.newLine();
      }
      bw.write(String.format("%d - %s - %.2f", id, name, mark));

    } catch (IOException e) {
      System.err.printf("%sError de escritura del fichero %s: %s%n", Colors.RED, FILE_TXT.toAbsolutePath(),
          e.getMessage());
      System.exit(1);
    }

  }

  public static void main(String[] args) {

    leerCSV();

  }
}