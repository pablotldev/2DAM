package ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import utils.Colors;

public class MostrarFicheros {
  private static final int ERR = 1;

  private MostrarFicheros() {
  }

  private static Process process() throws IOException {
    return new ProcessBuilder("cmd", "/c", "dir", "/b", "*A*").redirectErrorStream(true).start();
  }

  public static void main(String[] args) {
    try {
      Process p = process();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
          System.out.println(Colors.GREEN + line);
        }
        int exitCode = p.waitFor();
        System.out.println(Colors.GREEN + "El proceso terminó con código: " + Colors.PURPLE + exitCode);
        System.exit(exitCode);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println(Colors.RED + "Se ha interrumpido el proceso hijo: " + e.getMessage());
        System.exit(ERR);
      }
    } catch (IOException ex) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + ex.getMessage());
      System.exit(ERR);
    }

  }

}
