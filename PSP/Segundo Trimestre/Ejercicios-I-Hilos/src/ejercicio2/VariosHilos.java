package ejercicio2;

import utils.Colors;

public class VariosHilos {

  private VariosHilos() {
  }

  public static void main(String[] args) {

    for (int i = 1; i <= 5; i++) {
      new HiloC(5).start();
    }

    System.out.println(Colors.WHITE + "Todos los hilos creados");

  }

}
