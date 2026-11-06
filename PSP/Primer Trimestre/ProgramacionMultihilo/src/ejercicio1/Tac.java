package ejercicio1;

public class Tac extends Thread {

  private void tac() {
    try {
      Thread.sleep(500);
      while (true) {
        System.out.print("\u001B[34mTAC ");
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      System.err.println("Error en la clase Tac");
    }
  }

  @Override
  public void run() {
    this.tac();
  }

}
