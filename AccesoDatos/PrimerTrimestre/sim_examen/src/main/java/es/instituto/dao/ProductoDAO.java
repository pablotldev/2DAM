package es.instituto.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.instituto.model.Producto;

public interface ProductoDAO {

  List<Producto> maxPrice(Connection con, double precioMax) throws SQLException;

  void sell(Connection con, int id) throws SQLException;

  void show(Connection con) throws SQLException;

}
