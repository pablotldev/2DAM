package ejercicio4;

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

  private static final String HIJA = Main.class.getName();

  private static ProcessBuilder process(String[] args) {
    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    List<String> comando = new ArrayList<>(
        List.of(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJA));

    if (args != null)
      comando.addAll(Arrays.asList(args));

    return new ProcessBuilder(comando).redirectErrorStream(true);

  }

  private Launcher() {
  }

  private final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {

    List<String> valores = new ArrayList<>();
    try (READER) {
      while (true) {
        System.out.print(Colors.BLUE + "Introduce un número. Introduce 0 para terminar: " + Colors.WHITE);

        String value = READER.readLine().trim().replace(",", ".");

        if (value == null || !value.matches("\\d+(\\.\\d+)?")) {
          System.out.println(Colors.YELLOW + "Eso no es un número.");
          continue;
        }

        double num = Double.parseDouble(value);
        if (num == 0) {
          break;
        }
        valores.add(String.valueOf(num));
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
    }

    try {
      Process p = process(valores.toArray(String[]::new)).start();

      try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
        String out;
        while ((out = br.readLine()) != null) {
          System.out.println(Colors.GREEN + out);
        }
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de comunicación con el proceso hijo.");
      System.exit(1);
    }
  }

}
