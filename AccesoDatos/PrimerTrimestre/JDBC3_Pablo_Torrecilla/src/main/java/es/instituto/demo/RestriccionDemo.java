package es.instituto.demo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.instituto.db.ConnectionDB;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;
import es.instituto.utils.DBUtils;

public final class RestriccionDemo {
  private static final String INSERT = "INSERT INTO cuentas (id, titular, saldo) VALUES (?, ?, ?)";

  private RestriccionDemo() {
  }

  public static void ejecutar() {
    try {
      if (DBUtils.saldo(1) == null) {
        System.out.println(Colors.RED + "La cuenta 1 no existe. Ejecuta la opción 5 para restablecer los datos.");
        return;
      }
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
      return;
    }

    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT)) {

      ps.setInt(1, 1);
      ps.setString(2, "Duplicada");
      ps.setBigDecimal(3, new BigDecimal("0.00"));

      int filas = ps.executeUpdate();
      System.out.println(
          Colors.RED + "Se insertaron " + filas + " filas. La cuenta 1 no existía; comprueba el estado de la BD.");
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);

      if (e.getErrorCode() == 1062) {
        System.out.println(Colors.YELLOW
            + "La tabla cuentas tiene id como PRIMARY KEY. El id 1 ya existe, así que MySQL rechaza la fila duplicada con el error 1062.");
      } else if ("23000".equals(e.getSQLState())) {
        System.out.println(Colors.YELLOW + "Se ha incumplido una restricción de integridad de la base de datos.");
      }
    }

    try {
      DBUtils.imprimirCuentas("Datos después del intento fallido");
      DBUtils.imprimirMov();
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
    }
  }

}
