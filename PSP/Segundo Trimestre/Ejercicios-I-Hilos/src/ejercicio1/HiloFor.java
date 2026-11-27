package ejercicio1;

import utils.Colors;

public class HiloFor extends Thread {

  private void dentroHilo() {
    for (int i = 0; i < 10; i++) {
      System.out.print(Colors.GREEN);
      System.err.println("Estoy dentro del hilo");
    }

  }

  @Override
  public void run() {
    dentroHilo();
  }

}
