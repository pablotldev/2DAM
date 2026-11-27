package ejercicio1;

import utils.Colors;

public class DentroFuera {

  public static void main(String[] args) {
    new HiloFor().start();
    fueraHilo();
  }

  private static void fueraHilo() {
    for (int i = 0; i < 10; i++) {
      System.out.print(Colors.BLUE);
      System.out.println("Estoy fuera del hilo");
    }
  }

}
