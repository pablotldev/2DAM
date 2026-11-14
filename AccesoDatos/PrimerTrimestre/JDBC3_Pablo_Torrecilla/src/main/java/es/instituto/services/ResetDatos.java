package es.instituto.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.instituto.db.ConnectionDB;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;
import es.instituto.utils.DBUtils;

public class ResetDatos {

  private static final String BORRAR_MOV = "DELETE FROM movimientos";
  private static final String RESET_ACC = "INSERT INTO cuentas (id, titular, saldo) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE titular = ?, saldo = ?";

  private ResetDatos() {
  }

  public static void ejecutar() {
    System.out.println(Colors.PURPLE + "Restableciendo datos de prueba...");

    try (Connection con = ConnectionDB.getConnection()) {
      con.setAutoCommit(false);

      try {
        try (PreparedStatement ps = con.prepareStatement(BORRAR_MOV)) {
          ps.executeUpdate();
        }

        resetCuenta(con, 1, "Ana", new BigDecimal("500.00"));
        resetCuenta(con, 2, "Luis", new BigDecimal("100.00"));

        con.commit();

        System.out.println(Colors.GREEN + "Datos restablecidos: Ana 500.00€, Luis 100.00€ y 0 movimientos");
      } catch (SQLException e) {
        try {
          con.rollback();
        } catch (SQLException rollbackException) {
          e.addSuppressed(rollbackException);
        }
        throw e;
      } finally {
        try {
          con.setAutoCommit(true);
        } catch (SQLException e) {
        }
      }
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
      return;
    }

    try {
      DBUtils.imprimirCuentas("Estado actual");
      DBUtils.imprimirMov();
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
    }
  }

  private static void resetCuenta(Connection con, int id, String titular, BigDecimal saldo) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement(RESET_ACC)) {
      ps.setInt(1, id);
      ps.setString(2, titular);
      ps.setBigDecimal(3, saldo);
      ps.setString(4, titular);
      ps.setBigDecimal(5, saldo);
      ps.executeUpdate();
    }
  }

}
