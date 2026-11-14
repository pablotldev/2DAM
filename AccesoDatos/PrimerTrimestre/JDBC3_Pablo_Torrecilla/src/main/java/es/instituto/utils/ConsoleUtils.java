package es.instituto.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class ConsoleUtils {

  private ConsoleUtils() {
  }

  public static int leerInt(BufferedReader reader, String msg) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + msg + Colors.WHITE);
      String line = reader.readLine();
      if (line == null) {
        throw new IOException("Entrada cerrada");
      }

      try {
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.err.println(Colors.RED + "Introduce un número válido.");
      }
    }
  }

  public static BigDecimal leerBigD(BufferedReader reader, String msg) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + msg + Colors.WHITE);
      String line = reader.readLine();
      if (line == null) {
        throw new IOException("Entrada cerrada");
      }

      String val = line.trim().replace(',', '.');
      try {
        return new BigDecimal(val);
      } catch (NumberFormatException e) {
        System.err.println(Colors.RED + "Introduce un valor válido");
      }
    }
  }

  public static String formatear(BigDecimal val) {
    if (val == null) {
      return "0.00";
    }
    return val.setScale(2, RoundingMode.HALF_UP).toPlainString();
  }

  public static void imprimirSQLExc(SQLException e) {
    System.err.println(Colors.RED + "Error SQL: " + e.getMessage());
    System.err.println(Colors.RED + "SQLState: " + e.getSQLState());
    System.err.println(Colors.RED + "Código del fabricante: " + e.getErrorCode());
  }

}
