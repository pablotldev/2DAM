package ejercicio5;

import utils.Colors;

public class PruebaJoin {

  private PruebaJoin() {
  }

  public static void main(String[] args) {

    HiloJoin[] hilos = new HiloJoin[5];
    try {
      for (int i = 0; i < 5; i++) {
        hilos[i] = new HiloJoin(i + 1);
        hilos[i].start();
        hilos[i].join();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println(Colors.RED + "Se ha interrumpido la ejecución: " + e.getMessage());
      System.exit(1);
    }

    System.out.println(Colors.WHITE + "Todos los hilos creados");

  }

}
