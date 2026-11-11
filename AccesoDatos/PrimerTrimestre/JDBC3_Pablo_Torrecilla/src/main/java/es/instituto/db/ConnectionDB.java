package es.instituto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionDB {

  private static final Dotenv ENV = Dotenv.configure().ignoreIfMissing().load();

  private ConnectionDB() {
  }

  public static Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        ENV.get("DB_URL"),
        ENV.get("DB_USER"),
        ENV.get("DB_PASSWORD"));

    return con;
  }

  public static Connection getServerConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        ENV.get("DB_SERVER"),
        ENV.get("DB_USER"),
        ENV.get("DB_PASSWORD"));

    return con;
  }

}
