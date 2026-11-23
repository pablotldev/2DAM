package ejercicio4;

import utils.Colors;

public class Main {

  public static void main(String[] args) {

    double suma = 0;
    for (String val : args) {

      double num = Double.parseDouble(val);
      suma += num;

    }

    double avg = suma / args.length;
    System.out.println("La media es " + Colors.PURPLE + avg);

  }

}
