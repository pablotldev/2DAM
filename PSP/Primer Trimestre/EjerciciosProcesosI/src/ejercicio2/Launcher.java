package ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Launcher {

  private static final String HIJA = Main.class.getName();

  private static ProcessBuilder process(String n) {
    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    ProcessBuilder pb = new ProcessBuilder(java, "-Dstdout.encoding=UTF-8", "-cp", cp, HIJA, n);
    pb.redirectErrorStream(true);
    return pb;
  }

  private static void salida(Process p) throws IOException {

    BufferedReader bR = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));

    String line;
    while ((line = bR.readLine()) != null) {
      System.out.println(line);
    }

  }

  public static void main(String[] args) {

    try (BufferedReader bR = new BufferedReader(new InputStreamReader(System.in))) {

      System.out.print("Dame un número y te calculo el doble de este: ");
      String n = bR.readLine();

      Process p = process(n).start();
      salida(p);

    } catch (IOException e) {
      System.err.printf("Error de entrada/salida: %s", e.getMessage());
    }

  }

}
