package ejercicio1;

public class Tic extends Thread {

  private void tic() {
    try {
      while (true) {
        System.out.print("\u001B[32mTIC ");
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
