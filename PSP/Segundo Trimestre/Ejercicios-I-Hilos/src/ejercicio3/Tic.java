package ejercicio3;

import utils.Colors;

public class Tic extends Thread {

  private void tic() {
    try {
      while (true) {
        System.out.print(Colors.GREEN + "TIC ");
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      System.err.println("Error en la clase Tic");
    }
  }

  @Override
  public void run() {
    this.tic();
  }

}
