package es.instituto.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import es.instituto.db.ConnectionDB;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;
import es.instituto.utils.DBUtils;

public final class TransferenciaService {

  private static final String SQL_BLOQ_ACC = "SELECT id, titular, saldo FROM cuentas WHERE id IN (?, ?) order by id FOR UPDATE";
  private static final String UPDATE_DEBITO = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ? AND saldo >= ?";
  private static final String UPDATE_CREDITO = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?";
  private static final String INSERT_MOV = "INSERT INTO movimientos (origen, destino, importe) VALUES (?, ?, ?)";

  private TransferenciaService() {
  }

  public static void ejecutar(BufferedReader reader) throws IOException {
    int idOrigen = ConsoleUtils.leerInt(reader, "ID de la cuenta de origen: ");
    int idDestino = ConsoleUtils.leerInt(reader, "ID de la cuenta de destino: ");
    BigDecimal importe = ConsoleUtils.leerBigD(reader, "Importe a transferir (€): ");

    try {
      transferir(idOrigen, idDestino, importe, false);
      DBUtils.imprimirCuentas("Saldos después de la transferencia");
      DBUtils.imprimirMov();
    } catch (IllegalArgumentException e) {
      System.err.println(Colors.RED + "Datos inválidos: " + e.getMessage());
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
    }
  }

  public static boolean transferir(int idOrigen, int idDestino, BigDecimal importe, boolean fallo) throws SQLException {
    validar(idOrigen, idDestino, importe);
    BigDecimal importeNorm = normalizar(importe);

    try (Connection con = ConnectionDB.getConnection()) {
      con.setAutoCommit(false);

      try {
        Map<Integer, Cuenta> cuentas = bloquearCuentas(con, idOrigen, idDestino);
        Cuenta origen = cuentas.get(idOrigen);
        if (origen == null) {
          throw new SQLException("La cuenta de origen " + idOrigen + " no existe.");
        }

        Cuenta destino = cuentas.get(idDestino);
        if (destino == null) {
          throw new SQLException("La cuenta de destino " + idDestino + " no existe.");
        }

        if (origen.saldo().compareTo(importeNorm) < 0) {
          throw new SQLException("Saldo insuficiente en la cuenta " + idOrigen + ". Saldo actual: "
              + ConsoleUtils.formatear(origen.saldo()) + " €.");
        }

        int filasDebit;
        try (PreparedStatement ps = con.prepareStatement(UPDATE_DEBITO)) {
          ps.setBigDecimal(1, importeNorm);
          ps.setInt(2, idOrigen);
          ps.setBigDecimal(3, importeNorm);
          filasDebit = ps.executeUpdate();
        }
        if (filasDebit != 1) {
          throw new SQLException("No se pudo descontar el importe de la cuenta de origen.");
        }

        if (fallo) {
          throw new SQLException("Fallo simulado después de descontar y antes de sumar.");
        }

        int filasAbon;
        try (PreparedStatement ps = con.prepareStatement(UPDATE_CREDITO)) {
          ps.setBigDecimal(1, importeNorm);
          ps.setInt(2, idDestino);
          filasAbon = ps.executeUpdate();
        }
        if (filasAbon != 1) {
          throw new SQLException("No se pudo abonar el importe en la cuenta de destino.");
        }

        int filasMov;
        long idMov = -1;
        try (PreparedStatement ps = con.prepareStatement(INSERT_MOV, Statement.RETURN_GENERATED_KEYS)) {
          ps.setInt(1, idOrigen);
          ps.setInt(2, idDestino);
          ps.setBigDecimal(3, importeNorm);
          filasMov = ps.executeUpdate();

          try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
              idMov = rs.getLong(1);
            }
          }
        }
        if (filasMov != 1) {
          throw new SQLException("No se pudo registrar el movimiento.");
        }

        con.commit();

        System.out.printf("%sTransferencia confirmada %d -> %d | %s € | Movimiento %d%n",
            Colors.GREEN,
            idOrigen,
            idDestino,
            ConsoleUtils.formatear(importeNorm),
            idMov);

        System.out.printf("Saldos: %s = %s € | %s = %s €%n",
            origen.titular(),
            ConsoleUtils.formatear(origen.saldo().subtract(importeNorm)),
            destino.titular(),
            ConsoleUtils.formatear(destino.saldo().subtract(importeNorm)));

        return true;
      } catch (SQLException | RuntimeException e) {
        rollback(con, e);
        throw e;
      } finally {
        try {
          con.setAutoCommit(true);
        } catch (SQLException e) {
        }
      }
    }
  }

  private static void validar(int idOrigen, int idDestino, BigDecimal importe) {
    if (idOrigen <= 0 || idDestino <= 0) {
      throw new IllegalArgumentException("Los IDs deben ser mayores que cero.");
    }
    if (idOrigen == idDestino) {
      throw new IllegalArgumentException("La cuenta de origen y la de destino deben ser distintas.");
    }
    if (importe == null || importe.signum() <= 0) {
      throw new IllegalArgumentException("El importe debe ser mayor a cero.");
    }
  }

  private static BigDecimal normalizar(BigDecimal importe) {
    try {
      return importe.setScale(2, RoundingMode.UNNECESSARY);
    } catch (ArithmeticException e) {
      throw new IllegalArgumentException("El importe no puede tener más de dos decimales.");
    }
  }

  private static Map<Integer, Cuenta> bloquearCuentas(Connection con, int idOrigen, int idDestino) throws SQLException {
    Map<Integer, Cuenta> cuentas = new HashMap<>();

    try (PreparedStatement ps = con.prepareStatement(SQL_BLOQ_ACC)) {
      ps.setInt(1, idOrigen);
      ps.setInt(2, idDestino);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Cuenta acc = new Cuenta(
              rs.getInt("id"),
              rs.getString("titular"),
              rs.getBigDecimal("saldo"));
          cuentas.put(acc.id(), acc);
        }
      }
    }
    return cuentas;
  }

  private static void rollback(Connection con, Throwable causa) {
    try {
      con.rollback();
      System.out.println(Colors.YELLOW + "ROLLBACK ejecutado: no se confirma ningún cambio.");
    } catch (SQLException e) {
      causa.addSuppressed(e);
    }
  }

  private record Cuenta(int id, String titular, BigDecimal saldo) {
  }

}
