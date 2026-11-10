package es.instituto.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.instituto.models.Alumno;

public class AlumnoDAO {

  private static final String INSERT = """
      INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_nacimiento, Ciclo, Curso, Grupo)
      VALUES (?, ?, ?, ?, ?, ? , ?, ?)
      """;
  private static final String IMPORT = """
      INSERT INTO alumno
        (NIA, Nombre, Apellidos, Genero, Fecha_nacimiento, Ciclo, Curso, Grupo)
      VALUES (?, ?, ?, ?, ?, ?, ?, ?)
      ON DUPLICATE KEY UPDATE
        Nombre = ?,
        Apellidos = ?,
        Genero = ?,
        Fecha_nacimiento = ?,
        Ciclo = ?,
        Curso = ?,
        Grupo = ?
      """;

  private static final String SELECT_ALL = """
      SELECT * FROM alumno ORDER BY NIA
      """;

  private static final String SELECT_NIA = "SELECT * FROM alumno WHERE NIA = ?";
  private static final String UPDATE_NAME = "UPDATE alumno SET Nombre = ? WHERE NIA = ?";
  private static final String DELETE_NIA = "DELETE FROM alumno WHERE NIA = ?";
  private static final String DELETE_APELLIDOS = "DELETE FROM alumno WHERE Apellidos LIKE ?";
  private static final String EXISTS_NIA = "SELECT 1 FROM alumno WHERE NIA = ?";

  public void insert(Alumno a) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT)) {

      bindInsert(ps, a);
      if (ps.executeUpdate() != 1) {
        throw new SQLException("No se pudo insertar el alumno");
      }
    }
  }

  public List<Alumno> listar() throws SQLException {
    List<Alumno> alumnos = new ArrayList<>();

    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        alumnos.add(mapear(rs));
      }
    }
    return alumnos;
  }

  public Optional<Alumno> buscarNia(int nia) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_NIA)) {

      ps.setInt(1, nia);

      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
      }
    }
  }

  public boolean existeNia(int nia) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(EXISTS_NIA)) {

      ps.setInt(1, nia);

      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }

  public boolean updateNombre(int nia, String nombre) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(UPDATE_NAME)) {

      ps.setString(1, nombre);
      ps.setInt(2, nia);

      return ps.executeUpdate() == 1;
    }
  }

  public boolean delete(int nia) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(DELETE_NIA)) {

      ps.setInt(1, nia);
      return ps.executeUpdate() == 1;
    }
  }

  public int deleteApellidos(String apellido) throws SQLException {
    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(DELETE_APELLIDOS)) {

      ps.setString(1, "%" + apellido + "%");
      return ps.executeUpdate();
    }
  }

  public int insertLote(List<Alumno> alumnos) throws SQLException {
    if (alumnos == null || alumnos.isEmpty()) {
      return 0;
    }

    try (Connection con = ConnectionDB.getConnection();
        PreparedStatement ps = con.prepareStatement(IMPORT)) {

      con.setAutoCommit(false);

      try {
        for (Alumno al : alumnos) {
          bindInsert(ps, al);
          bindUpdate(ps, al);
          ps.addBatch();
        }

        ps.executeBatch();
        con.commit();
        return alumnos.size();
      } catch (SQLException e) {
        con.rollback();
        throw e;
      }
    }
  }

  private static Alumno mapear(ResultSet rs) throws SQLException {
    return new Alumno(
        rs.getInt("NIA"),
        rs.getString("Nombre"),
        rs.getString("Apellidos"),
        rs.getString("Genero"),
        rs.getDate("Fecha_nacimiento").toLocalDate(),
        rs.getString("Ciclo"),
        rs.getString("Curso"),
        rs.getString("Grupo").charAt(0));
  }

  private static void bindInsert(PreparedStatement ps, Alumno alumno) throws SQLException {
    ps.setInt(1, alumno.nia());
    ps.setString(2, alumno.nombre());
    ps.setString(3, alumno.apellidos());
    ps.setString(4, alumno.genero());
    ps.setDate(5, Date.valueOf(alumno.fechaNacimiento()));
    ps.setString(6, alumno.ciclo());
    ps.setString(7, alumno.curso());
    ps.setString(8, String.valueOf(alumno.grupo()));
  }

  private static void bindUpdate(PreparedStatement ps, Alumno alumno) throws SQLException {
    ps.setString(9, alumno.nombre());
    ps.setString(10, alumno.apellidos());
    ps.setString(11, alumno.genero());
    ps.setDate(12, Date.valueOf(alumno.fechaNacimiento()));
    ps.setString(13, alumno.ciclo());
    ps.setString(14, alumno.curso());
    ps.setString(15, String.valueOf(alumno.grupo()));
  }

}
