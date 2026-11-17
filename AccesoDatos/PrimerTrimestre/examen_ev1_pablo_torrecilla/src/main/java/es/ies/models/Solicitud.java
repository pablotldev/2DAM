package es.ies.models;

public class Solicitud {
  private String nombre;
  private int edad;
  private String ciclo;

  public Solicitud() {
  }

  public Solicitud(String nombre, int edad, String ciclo) {
    this.nombre = nombre;
    this.edad = edad;
    this.ciclo = ciclo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public String getCiclo() {
    return ciclo;
  }

  public void setCiclo(String ciclo) {
    this.ciclo = ciclo;
  }

}
