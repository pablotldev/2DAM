package es.instituto;

import java.io.IOException;
import java.sql.SQLException;

import es.instituto.db.InitializeDB;
import es.instituto.utils.Colors;

public class Main {

  private static void initialize() {
    try {
      InitializeDB.initialize();
    } catch (SQLException | IOException e) {
      System.err.printf("%sError de carga del archivo /resources/db/schema.sql: %s", Colors.RED, e.getMessage());
    }
  }

  public static void main(String[] args) {

    initialize();

  }

}
