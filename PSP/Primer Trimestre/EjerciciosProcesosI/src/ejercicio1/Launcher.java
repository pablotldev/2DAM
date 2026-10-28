package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Launcher {

  private static final String HIJO = Main.class.getName();
  private static final int ERR_GRAL = 1;

  private static ProcessBuilder process() {
    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    ProcessBuilder pb = new ProcessBuilder(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJO);

    pb.redirectErrorStream(true);
    return pb;

  }

  private static void salida(Process p) {
    try (BufferedReader bR = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {

      String line;
      while ((line = bR.readLine()) != null) {
        System.out.println(line);
      }

    } catch (IOException e) {
      System.err.printf("Error del proceso padre: %s%n", e.getMessage());
    }
  }

  public static void main(String[] args) {

    try {
      Process p = process().start();

      salida(p);

      int code = p.waitFor();
      System.out.printf("El proceso hijo ha terminado con el código %d%n", code);
    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
      System.exit(ERR_GRAL);
    } catch (InterruptedException e) {
      System.err.printf("Error: Se ha interrumpido el proceso: %s", e.getMessage());
      Thread.currentThread().interrupt();
      System.exit(ERR_GRAL);
    }

  }

}
