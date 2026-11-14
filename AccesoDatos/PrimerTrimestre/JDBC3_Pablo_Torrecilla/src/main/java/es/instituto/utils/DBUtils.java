package es.instituto.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.instituto.db.ConnectionDB;

public class DBUtils {

  private DBUtils() {
  }

  public static BigDecimal saldo(int id) throws SQLException {
    String sql = "SELECT saldo FROM cuentas WHERE id = ?";
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getBigDecimal("saldo");
        }
        return null;
      }
    }
  }

  public static int contarMov() throws SQLException {
    String sql = "SELECT COUNT(*) FROM movimientos";
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      rs.next();
      return rs.getInt(1);
    }
  }

  public static void imprimirCuentas(String tag) throws SQLException {
    String sql = "SELECT id, titular, saldo FROM cuentas ORDER BY id";
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      System.out.println(Colors.PURPLE + tag);
      boolean hay = false;
      while (rs.next()) {
        hay = true;
        System.out.printf("%s  ID: %d | %s | %s€%n",
            Colors.GREEN,
            rs.getInt("id"),
            rs.getString("titular"),
            ConsoleUtils.formatear(rs.getBigDecimal("saldo")));
      }
      if (!hay) {
        System.out.println(Colors.YELLOW + " No hay cuentas.");
      }
    }
  }

  public static void imprimirMov() throws SQLException {
    String sql = "SELECT id, origen, destino, importe FROM movimientos ORDER BY origen, id";
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      System.out.println(Colors.PURPLE + "Movimientos");
      boolean hay = false;
      while (rs.next()) {
        if (!hay) {
          hay = true;
        }
        System.out.printf("%s  ID: %d | Origen: %d | Destino: %d | Importe: %s%n",
            Colors.GREEN,
            rs.getInt("id"),
            rs.getInt("origen"),
            rs.getInt("destino"),
            ConsoleUtils.formatear(rs.getBigDecimal("importe")));
      }
      if (!hay) {
        System.out.println(Colors.YELLOW + " No se encontraron registros.");
      }
    }
  }

}
