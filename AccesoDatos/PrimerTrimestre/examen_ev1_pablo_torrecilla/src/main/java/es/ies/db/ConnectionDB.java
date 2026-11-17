package es.ies.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import es.ies.utils.Colors;
import io.github.cdimascio.dotenv.Dotenv;

public final class ConnectionDB {

  private static final Dotenv ENV = Dotenv.configure().ignoreIfMissing().load();

  private ConnectionDB() {
  }

  public static Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        ENV.get("URL_DB"),
        ENV.get("USER"),
        ENV.get("PASS"));

    System.out.printf("%sConexión establecida en la DB: %s.%s%n", Colors.GREEN, con.getCatalog(), Colors.RESET);
    return con;
  }

}
