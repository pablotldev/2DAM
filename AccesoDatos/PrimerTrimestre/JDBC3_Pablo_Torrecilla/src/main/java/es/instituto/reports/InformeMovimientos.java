package es.instituto.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.instituto.db.ConnectionDB;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;

public final class InformeMovimientos {
  private static final String MOVIMIENTOS = "SELECT id, origen, destino, importe FROM movimientos ORDER BY origen, id";
  private static final String TOTALES = "SELECT m.origen, c.titular, SUM(m.importe) AS total FROM movimientos m JOIN cuentas c ON c.id = m.origen GROUP BY m.origen, c.titularORDER BY m.origen";

  private InformeMovimientos() {
  }

  public static void ejecutar() {
    try (Connection con = ConnectionDB.getConnection()) {
      boolean hayMov = listarMov(con);

      if (hayMov) {
        listarTotales(con);
      } else {
        System.out.println(Colors.YELLOW + "No se encontraron registros. No hay totales que calcular.");
      }
    } catch (SQLException ex) {
      ConsoleUtils.imprimirSQLExc(ex);
    }
  }

  private static boolean listarMov(Connection con) throws SQLException {
    System.out.println(Colors.BLUE + "Movimientos ordenados por cuenta de origen e identificador: ");

    boolean hay = false;

    try (PreparedStatement ps = con.prepareStatement(MOVIMIENTOS);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        if (!hay) {
          hay = true;
        }
        System.out.printf("%s  ID: %d | Origen: %d | Destino: %d | Importe: %s€%n",
            Colors.GREEN,
            rs.getInt("id"),
            rs.getInt("origen"),
            rs.getInt("destino"),
            ConsoleUtils.formatear(rs.getBigDecimal("importe")));
      }
    }
    return hay;
  }

  private static void listarTotales(Connection con) throws SQLException {
    System.out.println(Colors.BLUE + "Total transferido por cuenta de origen: ");

    try (PreparedStatement ps = con.prepareStatement(TOTALES);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%s  Origen: %d | %s | Total: %s €%n",
            Colors.GREEN,
            rs.getInt("origen"),
            rs.getString("titular"),
            ConsoleUtils.formatear(rs.getBigDecimal("total")));
      }
    }
  }
}
