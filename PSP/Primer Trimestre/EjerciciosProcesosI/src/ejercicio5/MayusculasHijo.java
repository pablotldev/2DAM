package ejercicio5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import utils.Colors;

public class MayusculasHijo {

  private MayusculasHijo() {
  }

  public static void main(String[] args) {
    try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

      String line;
      while ((line = entrada.readLine()) != null) {
        System.out.println(Colors.PURPLE + line.toUpperCase() + Colors.WHITE);
        System.out.flush();
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida en el proceso hijo: " + e.getMessage());
    }
  }
}
