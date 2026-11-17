package es.ies.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.ies.models.Alumno;
import es.ies.models.Solicitud;
import es.ies.utils.Colors;

public class AlumnoDAOImpl implements AlumnoDAO {

  @Override
  public List<Alumno> buscarAlumno(Connection con, int edadMin, int edadMax) throws SQLException {
    String sql = "SELECT id, nombre, edad, ciclo FROM alumnos WHERE edad >= ? AND edad <= ? ORDER BY edad DESC, id ASC";
    List<Alumno> alumnos = new ArrayList<>();

    try (PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, edadMin);
      ps.setInt(2, edadMax);

      try (ResultSet rs = ps.executeQuery()) {

        boolean flag = false;

        while (rs.next()) {
          if (!flag) {
            flag = true;
          }
          alumnos.add(new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"), rs.getString("ciclo")));
        }

        if (!flag) {
          System.out.println(Colors.YELLOW + "No se han encontrado alumnos con ese rango (min: "
              + Colors.PURPLE + edadMin + Colors.YELLOW + ", max: " + Colors.PURPLE + edadMax);
          return alumnos;
        }

      }

    }
    return alumnos;

  }

  @Override
  public void insertarAlumnos(Connection con, List<Solicitud> solicitudes) throws SQLException {
    con.setAutoCommit(false);

    String sql = "INSERT INTO alumnos (nombre, edad, ciclo) VALUES (?, ?, ?)";
    try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      for (Solicitud sol : solicitudes) {

        String nombre = sol.getNombre();
        int edad = sol.getEdad();
        String ciclo = sol.getCiclo();
        ps.setString(1, nombre);
        ps.setInt(2, edad);
        ps.setString(3, ciclo);

        ps.executeUpdate();
        boolean flag = false;
        try (ResultSet key = ps.getGeneratedKeys()) {
          while (key.next()) {
            if (!flag) {
              flag = true;
            }
            int id = key.getInt(1);

            System.out.printf("%s  ID: %s%d%s | Nombre: %s%s%s | Edad: %s%d%s | Ciclo: %s%s%n", Colors.GREEN,
                Colors.PURPLE, id, Colors.GREEN, Colors.PURPLE, nombre, Colors.GREEN, Colors.PURPLE, edad,
                Colors.GREEN, Colors.PURPLE, ciclo);
          }
        } catch (SQLException e) {
          System.err.println(Colors.RED + "Error al insertar los nuevos datos en la BD: " + e.getMessage());
          con.rollback();
          return;
        }
        con.commit();
        if (!flag) {
          System.out.println(Colors.YELLOW + "No se ha insertado ningún valor en la DB. Comprueba el fichero XML.");
        }
      }

    } finally {
      con.setAutoCommit(true);
    }
  }

}
