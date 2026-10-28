package es.instituto;

public class Main {
  public static void main(String[] args) {
    ListarAlumnos.selectAlumnos();
    AlumnosPorCiclo.buscarPorCiclo("dam");
    AlumnosMayores.edadMinCiclo();
    ResumenAlumnos.resumen();
  }
}
