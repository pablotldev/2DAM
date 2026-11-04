package es.instituto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import es.instituto.utils.Colors;
import io.github.cdimascio.dotenv.Dotenv;

public final class ConnectionDB {

  private static final Dotenv env = Dotenv.configure().ignoreIfMissing().load();

  private ConnectionDB() {
  }

  public static Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        env.get("DB_URL"),
        env.get("DB_USER"),
        env.get("DB_PASSWORD"));

    System.out.printf("%sConexión establecida en la DB: %s.%s%n", Colors.GREEN, con.getCatalog(), Colors.RESET);
    return con;
  }

  public static Connection getServerConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        env.get("DB_URL_SERVER"),
        env.get("DB_USER"),
        env.get("DB_PASSWORD"));

    System.out.printf("%sConexión establecida a MySQL.%n", Colors.GREEN);
    return con;
  }
}
