package es.instituto.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import es.instituto.utils.Colors;

public class Notas {

  private static final Path FILE_CSV = Path.of("notas.csv");
  private static final Path FILE_TXT = Path.of("aprobados.txt");

  public static void main() throws IOException {

    if (Files.notExists(FILE_CSV)) {
      Files.createFile(FILE_CSV);
    }

    try (BufferedReader br = new BufferedReader(new FileReader(FILE_CSV.toFile(), StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_TXT.toFile(), StandardCharsets.UTF_8))) {

      List<String> alumnos = br.lines().skip(1).filter(f -> !f.isBlank()).toList();
      int valid = 0, pass = 0;
      double avg = 0;
      for (String a : alumnos) {
        String[] al = a.split(";");

        String strId = al[0];
        if (strId == null || strId.isBlank()) {
          System.out.println(Colors.RED + "El ID no puede estar vacío.");
          continue;
        }

        if (!strId.matches("\\d+")) {
          System.out.println(Colors.RED + "El ID debe estar compuesto únicamente por números.");
          continue;
        }

        String nombre = al[1];
        if (nombre == null || nombre.isBlank()) {
          System.out.println(Colors.RED + "El nombre no puede estar vacío.");
          continue;
        }

        if (!nombre.matches("\\p{L}+")) {
          System.out.println(Colors.RED + "El nombre debe estar compuesto únicamente por letras.");
          continue;
        }

        String strNota = al[2];
        if (strNota == null || strNota.isBlank()) {
          System.out.println(Colors.RED + "El nota no puede estar vacía.");
          continue;
        }

        if (!strNota.matches("\\d+(.\\d+)*")) {
          System.out.println(Colors.RED + "El nota debe estar compuesto únicamente por letras.");
          continue;
        }

        int id = Integer.parseInt(strId);
        double nota = Double.parseDouble(strNota);

        avg += nota;
        valid++;
        if (nota < 5) {
          continue;
        }

        pass++;
        bw.write(id + " - " + nombre + " - " + nota);
        bw.newLine();
      }

      avg /= valid;
      System.out.printf("%s%d%s registros válidos, %s%d%s aprobados y media %s%.2f", Colors.PURPLE, valid, Colors.GREEN,
          Colors.PURPLE, pass, Colors.GREEN, Colors.PURPLE, avg);

    }

  }

}
