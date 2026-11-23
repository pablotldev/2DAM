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

public class Mayusculas {

  private static final String HIJO = MayusculasHijo.class.getName();
  private static final String ENDER = "salir";

  private Mayusculas() {
  }

  private static Process process() throws IOException {
    String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();
    String cp = System.getProperty("java.class.path");

    List<String> command = new ArrayList<>(
        List.of(java, "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", cp, HIJO));

    return new ProcessBuilder(command).redirectErrorStream(true).start();
  }

  public static void main(String[] args) {
    try {
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
            System.out.println(respuesta);
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
