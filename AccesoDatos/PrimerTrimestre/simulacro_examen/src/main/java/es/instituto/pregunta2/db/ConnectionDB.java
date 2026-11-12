package es.instituto.pregunta2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import es.instituto.utils.Colors;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionDB {

  private static final Dotenv ENV = Dotenv.configure().ignoreIfMissing().load();

  private ConnectionDB() {
  }

  public static Connection getConnectionDB() throws SQLException {
    Connection con = DriverManager.getConnection(
        ENV.get("DB_URL"),
        ENV.get("SERVER_USER"),
        ENV.get("SERVER_PASS"));
    System.out.println(Colors.GREEN + "Conexión exitosa a " + Colors.PURPLE + con.getCatalog());
    return con;
  }

  public static Connection getServerConnection() throws SQLException {
    Connection con = DriverManager.getConnection(
        ENV.get("SERVER_URL"),
        ENV.get("SERVER_USER"),
        ENV.get("SERVER_PASS"));
    System.out.println(Colors.GREEN + "Conexión exitosa a MySQL");
    return con;
  }

}
