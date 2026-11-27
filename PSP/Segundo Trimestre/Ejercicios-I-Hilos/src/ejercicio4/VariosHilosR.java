package ejercicio4;

import utils.Colors;

public class VariosHilosR {

  private VariosHilosR() {
  }

  public static void main(String[] args) {

    for (int i = 1; i <= 5; i++) {
      new HiloCR(i).run();
    }

    System.out.println(Colors.WHITE + "Todos los hilos creados");
  }

}
