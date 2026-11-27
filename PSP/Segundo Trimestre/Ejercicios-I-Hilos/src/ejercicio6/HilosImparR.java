package ejercicio6;

import utils.Colors;

public class HilosImparR implements Runnable {

  public HilosImparR() {
  }

  @Override
  public void run() {
    for (int i = 1; i <= 10; i++) {
      if (i % 2 != 0) {
        System.out.println(Colors.GREEN + i);
      }
    }

  }

}
