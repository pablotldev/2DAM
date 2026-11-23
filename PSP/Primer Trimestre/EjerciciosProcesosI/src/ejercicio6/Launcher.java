package ejercicio6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Colors;

public class Launcher {

  private static final String HIJO = Sumador.class.getName();

  private Launcher() {
  }

  private static Process process(String[] args) throws IOException {

    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    List<String> cmd = new ArrayList<>(
        List.of(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJO));

    if (args != null) {
      cmd.addAll(Arrays.asList(args));
    }

    return new ProcessBuilder(cmd).redirectErrorStream(true).start();
  }

  public static void main(String[] args) {

    String[] param = new String[2];
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

      while (true) {
        System.out.print(Colors.BLUE + "[Entrada 1] Introduce un número entero: " + Colors.WHITE);
        String s = reader.readLine();

        if (s == null || !s.matches("\\d+")) {
          System.out.println(Colors.YELLOW + "Introduce un número válido");
          continue;
        }
        param[0] = s;
        break;
      }
      while (true) {
        System.out.print(Colors.BLUE + "[Entrada 2] Introduce otro número entero: " + Colors.WHITE);
        String s = reader.readLine();

        if (s == null || !s.matches("\\d+")) {
          System.out.println(Colors.YELLOW + "Introduce un número válido");
          continue;
        }
        param[1] = s;
        break;
      }
      Arrays.sort(param);

      try {
        Process p = process(param);
        try (BufferedReader salidaHijo = new BufferedReader(
            new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
          String line;
          if ((line = salidaHijo.readLine()) != null) {
            System.out.println(line);
          }
          p.waitFor();
        }
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        System.err.println(Colors.RED + "Se ha interrumpido el proceso hijo: " + ex.getMessage());
        System.exit(1);
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
    }
  }
}
