package ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Launcher_rs {

  private static final int ERR_GRAL = 1;
  private static final String END = "fin";
  private static final String EXE = System.getProperty("os.name").toLowerCase().contains("win") ? "random.exe"
      : "random";
  private static final Path BIN = Path.of("target", "release", EXE);

  private static void compilar() throws IOException, InterruptedException {

    ProcessBuilder pb = new ProcessBuilder("cargo", "build", "--release", "--bin", "random");
    pb.redirectErrorStream(true);
    Process p = pb.start();

    salida(p);

    if (p.waitFor() != 0)
      throw new IOException("cargo build ha fallado");
  }

  private static ProcessBuilder process() {
    ProcessBuilder pb = new ProcessBuilder(BIN.toString());
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
      compilar();
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
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

  }

}
