package es.instituto.demo;

import java.math.BigDecimal;
import java.sql.SQLException;

import es.instituto.services.TransferenciaService;
import es.instituto.utils.Colors;
import es.instituto.utils.ConsoleUtils;
import es.instituto.utils.DBUtils;

public final class RollbackDemo {
  private RollbackDemo() {
  }

  public static void ejecutar() {
    int idOrigen = 1;
    int idDestino = 2;
    BigDecimal importe = new BigDecimal("50.00");

    try {
      BigDecimal saldoOrigenAntes = DBUtils.saldo(idOrigen);
      BigDecimal saldoDestinoAntes = DBUtils.saldo(idDestino);
      int movAntes = DBUtils.contarMov();

      if (saldoOrigenAntes == null || saldoDestinoAntes == null) {
        System.out.println(Colors.RED + "Faltan las cuentas 1 o 2. Ejecuta la opción 5 para restablecer los datos.");
        return;
      }

      DBUtils.imprimirCuentas("Saldos antes del fallo");
      System.out.println(Colors.BLUE + "Movimientos antes: " + movAntes);
      try {
        TransferenciaService.transferir(idOrigen, idDestino, importe, true);
        System.out.println(Colors.RED + "ERROR: La transferencia no debió confirmarse");
      } catch (SQLException ex) {
        System.err.println(Colors.YELLOW + "Fallo controlado: " + ex.getMessage());
      }

      BigDecimal saldoOrigenDespues = DBUtils.saldo(idOrigen);
      BigDecimal saldoDestinoDespues = DBUtils.saldo(idDestino);
      int movDespues = DBUtils.contarMov();

      DBUtils.imprimirCuentas("Saldos después del fallo");
      System.out.println(Colors.BLUE + "Movimientos después: " + movDespues);

      boolean saldosIguales = mismoSaldo(saldoOrigenAntes, saldoOrigenDespues)
          && mismoSaldo(saldoDestinoAntes, saldoDestinoDespues);
      boolean sinMovParcial = movAntes == movDespues;

      if (saldosIguales && sinMovParcial) {
        System.out.println(Colors.GREEN + "ROLLBACK correcto: saldos intactos y sin movimiento parcial.");
      } else {
        System.out.println(Colors.RED + "ERROR: el rollback no ha dejado la base de datos como estaba");
      }
    } catch (SQLException e) {
      ConsoleUtils.imprimirSQLExc(e);
    }
  }

  private static boolean mismoSaldo(BigDecimal antes, BigDecimal despues) {
    return antes != null && despues != null && antes.compareTo(despues) == 0;
  }
}
