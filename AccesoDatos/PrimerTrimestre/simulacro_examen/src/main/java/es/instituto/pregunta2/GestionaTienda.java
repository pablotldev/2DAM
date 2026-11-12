package es.instituto.pregunta2;

import java.io.IOException;
import java.sql.SQLException;

import es.instituto.pregunta2.db.InitializeDB;
import es.instituto.utils.Colors;

public class GestionaTienda {

  private GestionaTienda() {
  }

  private static void initialize() {
    try {
      InitializeDB.initialize();
    } catch (SQLException | IOException e) {
      System.err.printf("%sError de carga del archivo /resources/db/schema.sql: %s", Colors.RED, e.getMessage());
      System.exit(1);
    }
  }

  public static void main(String[] args) {

    initialize();

  }
}
