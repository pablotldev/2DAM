package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import utils.Colors;

public class LanzadorPadre {
  private static final int ERR = 1;
  private static final int SUCC = 0;
  private static final String HIJO = TransformadorTexto.class.getName();

  private LanzadorPadre() {
  }

  private static Process process(String param) throws IOException {

    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    List<String> cmd = new ArrayList<>(
        List.of(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJO));

    if (param != null && !param.isEmpty()) {
      cmd.add(param);
    }

    return new ProcessBuilder(cmd).redirectErrorStream(true).start();

  }

  public static void main(String[] args) {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

      String param;
      while (true) {
        System.out.print(Colors.BLUE + "Introduce una cadena de texto: " + Colors.WHITE);
        param = reader.readLine();
        if (param.isBlank()) {
          System.out.println(Colors.YELLOW + "Introduce una cadena de texto válida" + Colors.WHITE);
          continue;
        }
        break;
      }

      try {
        Process p = process(param);
        try (BufferedReader outHijo = new BufferedReader(
            new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
          String line;
          while ((line = outHijo.readLine()) != null) {
            System.out.println(line + ". Tiene " + Colors.PURPLE + line.length() + Colors.GREEN + " caracteres.");
          }
        }
        p.waitFor();

      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        System.err.println(Colors.RED + "Se ha interrumpido el proceso hijo: " + ex.getMessage());
      }

    } catch (IOException e) {
      System.err.println("Error de entrada/salida: " + e.getMessage());
      System.exit(ERR);
    }

    System.exit(SUCC);

  }

}
