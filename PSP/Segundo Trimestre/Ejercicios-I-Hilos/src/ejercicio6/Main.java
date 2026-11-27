package ejercicio6;

public class Main {

  public static void main(String[] args) {

    new HilosImparR().run();
    new HilosParR().run();
    new HilosImparT().start();
    new HilosParT().start();

  }

}
