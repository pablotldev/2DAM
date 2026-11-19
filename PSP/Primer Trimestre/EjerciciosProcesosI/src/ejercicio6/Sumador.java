package ejercicio6;

import utils.Colors;

public class Sumador {

  private Sumador() {
  }

  private static int suma(int min, int max) {
    if (min == max) {
      return min + max;
    }
    
    int suma = 0;
    for (int i = min; i <= max; i++) {
      suma += i;
    }

    return suma;
  }

  public static void main(String[] args) {

    if (args.length != 2) {
      System.err.println(Colors.RED + "Debes pasar al menos dos números.");
    }

    int suma = suma(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

    System.out.println(Colors.GREEN + "El total de la suma es " + Colors.PURPLE + suma);

  }

}
