package ejercicio5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import utils.Colors;

public class Launcher_rs {

  private static final String EXE = System.getProperty("os.name").toLowerCase().contains("win") ? "reader.exe"
      : "reader";
  private static final Path BIN = Path.of("target", "release", EXE);
  private static final String ENDER = "salir";

  private Launcher_rs() {
  }

  private static void compilar() throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder("cargo", "build", "--release", "--bin", "reader");
    pb.redirectErrorStream(true);
    Process p = pb.start();

    salida(p);

    if (p.waitFor() != 0)
      throw new IOException("Cargo build ha fallado");
  }

  private static Process process() throws IOException {
    List<String> cmd = new ArrayList<>(List.of(BIN.toString()));
    return new ProcessBuilder(cmd).redirectErrorStream(true).start();
  }

  private static void salida(Process p) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de lectura del hijo: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    try {
      compilar();
      Process p = process();

      try (
          BufferedReader salidaHijo = new BufferedReader(
              new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
          BufferedWriter entradaHijo = new BufferedWriter(
              new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
          BufferedReader entradaPadre = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

        System.out.println(Colors.BLUE + "Introduce líneas de texto. Introduce " + Colors.YELLOW + "\"Salir\""
            + Colors.BLUE + " para finalizar." + Colors.WHITE);
        String line;
        while ((line = entradaPadre.readLine()) != null) {
          if (line.equalsIgnoreCase(ENDER)) {
            break;
          }

          entradaHijo.write(line);
          entradaHijo.newLine();
          entradaHijo.flush();

          String respuesta = salidaHijo.readLine();
          if (respuesta != null) {
            System.out.println(Colors.PURPLE + respuesta + Colors.WHITE);
          }
        }
        entradaHijo.close();
        salidaHijo.close();

        int exit_code = p.waitFor();
        System.out.println(Colors.GREEN + "Proceso hijo finalizado con código: " + Colors.PURPLE + exit_code);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println(Colors.RED + "Se ha interrumpido la ejecución del proceso hijo: " + e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
      System.exit(1);
    }
  }

}
