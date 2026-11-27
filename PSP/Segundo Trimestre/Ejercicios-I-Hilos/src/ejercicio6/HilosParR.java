package ejercicio6;

import utils.Colors;

public class HilosParR extends Thread {

  public HilosParR() {
  }

  @Override
  public void run() {
    for (int i = 1; i <= 10; i++) {
      if (i % 2 == 0) {
        System.out.println(Colors.RED + i);
      }
    }

  }

}
