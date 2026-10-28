package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  private static final int ERR_GRAL = 1;
  private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {

    try (BR) {

      System.out.print("Introduce un número y yo te diré sus divisores: ");
      int n = Integer.parseInt(BR.readLine());
      int m = 0;

      for (int i = 1; i <= n; i++) {
        if (n % i == 0) {
          m++;
        }
      }
      System.out.printf("Número de divisores de %d: %d%n", n, m);

    } catch (NumberFormatException e) {
      System.err.println("Introduce un formato numérico válido.");
      System.exit(ERR_GRAL);
    } catch (IOException e) {
      System.err.println("Error de entrada/salida.");
    }

  }

}
