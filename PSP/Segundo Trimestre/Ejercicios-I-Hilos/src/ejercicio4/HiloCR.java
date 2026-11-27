package ejercicio4;

import utils.Colors;

public class HiloCR implements Runnable {

  private int c;

  public HiloCR(int c) {
    System.out.println(Colors.YELLOW + "Creando hilo " + c);
    this.c = c;
  }

  public HiloCR() {
  }

  public int getC() {
    return c;
  }

  public void setC(int c) {
    this.c = c;
  }

  @Override
  public void run() {
    for (int i = 1; i <= 5; i++) {
      System.out.println(Colors.GREEN + "Hilo " + Colors.PURPLE + this.c + Colors.GREEN + " línea " + Colors.BLUE + i);
    }
  }

}
