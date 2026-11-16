package es.instituto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.instituto.model.Producto;
import es.instituto.utils.Colors;

public class ProductoDAOImpl implements ProductoDAO {

  private static Producto mapper(ResultSet rs) throws SQLException {
    return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"));
  }

  @Override
  public List<Producto> maxPrice(Connection con, double precioMax) throws SQLException {
    String sql = "SELECT id, nombre, precio, stock FROM productos WHERE precio <= ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setDouble(1, precioMax);

      List<Producto> prods = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          prods.add(mapper(rs));
        }

      }
      return prods;
    }
  }

  @Override
  public void sell(Connection con, int id) throws SQLException {
    String sql = "UPDATE productos SET stock = stock - 1 WHERE id = ? AND stock > 0";

    try (PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, id);

      boolean update = ps.executeUpdate() > 0;

      if (!update) {
        System.out.println(Colors.YELLOW + "No se ha realizado ninguna venta.");
        return;
      }
      System.out.println(Colors.GREEN + "Venta realizada correctamente.");

    }
  }

  @Override
  public void show(Connection con) throws SQLException {
    String sql = "SELECT * FROM productos";
    try (PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%s  ID: %d | Nombre: %s | Precio: %.2f | Stock: %d%n", Colors.BLUE, rs.getInt("id"),
            rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"));
      }
    }
  }

}