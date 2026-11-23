package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import utils.Colors;

public class TransformadorTexto {

  private static final int ERR = 1;
  private static final int SUCC = 0;

  private TransformadorTexto() {
  }

  public static void main(String[] args) {

    if (args.length == 0) {
      System.err.println(Colors.RED + "Introduce al menos un argumento.");
      System.exit(ERR);
    }

    List<String> words = new ArrayList<>();
    for (String word : args) {
      words.add(word.trim().toUpperCase());
    }

    String line = String.join(" - ", words);

    System.out.println(Colors.GREEN + line);
    System.exit(SUCC);
  }
}