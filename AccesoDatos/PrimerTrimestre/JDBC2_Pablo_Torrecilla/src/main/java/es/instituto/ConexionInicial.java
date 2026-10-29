package es.instituto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConexionInicial {

  private static final Dotenv env = Dotenv.load();

  public static Connection getConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        env.get("DB_URL"),
        env.get("DB_USER"),
        env.get("DB_PASSWORD"));

    System.out.printf("Conexión establecida en la DB: %s.%n", con.getCatalog());
    return con;
  }
}
