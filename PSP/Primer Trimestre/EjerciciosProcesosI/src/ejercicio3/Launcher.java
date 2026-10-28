package ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Launcher {

  private static final int ERR_GRAL = 1;
  private static final String HIJA = Main.class.getName();
  private static final String END = "fin";

  private static ProcessBuilder process() {

    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    ProcessBuilder pb = new ProcessBuilder(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJA);
    pb.redirectErrorStream(true);

    return pb;
  }

  private static void salida(Process p) {
    try {
      BufferedReader bR = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = bR.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.err.printf("Error de lectura del hijo: %s", e.getMessage());
    }
  }

  public static void main(String[] args) {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))) {
      String ender = "";
      while (!END.equalsIgnoreCase(ender)) {
        Process p = process().start();
        salida(p);
        System.out.print("Introduce 'fin' para salir. 'Enter' para otro número: ");
        ender = bR.readLine();
      }

    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
      System.exit(ERR_GRAL);
    }

  }

}
