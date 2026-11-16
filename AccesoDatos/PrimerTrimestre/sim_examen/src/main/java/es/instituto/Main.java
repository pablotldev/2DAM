package es.instituto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.instituto.classes.Notas;
import es.instituto.dao.ProductoDAO;
import es.instituto.dao.ProductoDAOImpl;
import es.instituto.db.ConnectionDB;
import es.instituto.db.DBInitializer;
import es.instituto.model.Producto;
import es.instituto.utils.Colors;

public class Main {

  private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

  private static void initialize() {
    try {
      DBInitializer.initialize();
      System.out.println(Colors.GREEN + "Conexión exitosa a MySQL.");
    } catch (IOException | SQLException e) {
      System.err.println(Colors.RED + "Error de carga de la base de datos: " + e.getMessage());
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    try {
      Notas.main();
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
    }

    initialize();
    ProductoDAO dao = new ProductoDAOImpl();
    try {
      while (true) {
        System.out.print(Colors.BLUE + "Introduce el precio máximo para buscar los productos con menor precio: "
            + Colors.WHITE);
        String precioMaxStr;
        precioMaxStr = READER.readLine();

        if (precioMaxStr == null || precioMaxStr.isBlank()) {
          System.out.println(Colors.RED + "El precio máximo no puede estar vacío");
          continue;
        }

        if (!precioMaxStr.matches("\\d+(.\\d+)*")) {
          System.out.println(Colors.RED + "Introduce un precio máximo válido.");
          continue;
        }

        try (Connection con = ConnectionDB.getDBConnection()) {
          List<Producto> prods = dao.maxPrice(con, Double.parseDouble(precioMaxStr));
          if (prods.isEmpty()) {
            System.out.println(Colors.YELLOW + "No hay coincidencias");
            break;
          }
          prods.forEach(System.out::println);
          break;
        } catch (SQLException ex) {
          System.err.println(Colors.RED + "Error en la BD: " + ex.getMessage());
        }

      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de Entrada/Salida: " + e.getMessage());
    }

    try (Connection con = ConnectionDB.getDBConnection()) {
      dao.show(con);
    } catch (SQLException ex) {
      System.err.println(Colors.RED + "Error en la BD: " + ex.getMessage());
    }

    try {
      while (true) {
        System.out.print(Colors.BLUE + "Introduce el id del producto del que se ha realizado la venta: "
            + Colors.WHITE);
        String idStr;
        idStr = READER.readLine();

        if (idStr == null || idStr.isBlank()) {
          System.out.println(Colors.RED + "El id no puede estar vacío");
          continue;
        }

        if (!idStr.matches("\\d+")) {
          System.out.println(Colors.RED + "Introduce un id válido.");
          continue;
        }

        try (Connection con = ConnectionDB.getDBConnection()) {
          dao.sell(con, Integer.parseInt(idStr));
          break;
        } catch (SQLException ex) {
          System.err.println(Colors.RED + "Error en la BD: " + ex.getMessage());
        }
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de Entrada/Salida: " + e.getMessage());
    }

    try (Connection con = ConnectionDB.getDBConnection()) {
      dao.show(con);
    } catch (SQLException ex) {
      System.err.println(Colors.RED + "Error en la BD: " + ex.getMessage());
    }

  }
}