package es.ies.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.ies.models.Alumno;
import es.ies.models.Solicitud;

public interface AlumnoDAO {

  List<Alumno> buscarAlumno(Connection con, int edadMin, int edadMax) throws SQLException;

  void insertarAlumnos(Connection con, List<Solicitud> solicitudes) throws SQLException;

}