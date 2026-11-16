package es.instituto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionDB {

  private static final Dotenv ENV = Dotenv.configure().ignoreIfMissing().load();

  private ConnectionDB() {
  }

  public static Connection getDBConnection() throws SQLException {
    return DriverManager.getConnection(
        ENV.get("URL_DB"),
        ENV.get("USER"),
        ENV.get("PASS"));
  }

  public static Connection getServerConnection() throws SQLException {
    return DriverManager.getConnection(
        ENV.get("URL_SERVER"),
        ENV.get("USER"),
        ENV.get("PASS"));
  }

}
