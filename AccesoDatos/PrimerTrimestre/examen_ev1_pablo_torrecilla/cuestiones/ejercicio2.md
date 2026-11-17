# Respuestas del ejercicio 2

## Pablo Torrecilla

### RA2.1 (1 punto). (1 punto). Para edades de 20 a 21, indica qué valor y tipo enlazas a cada marcador SQL. Si la tabla contiene solo el lote de muestra, indica qué alumnos devuelve y en qué orden

[Referencia](..\src\main\java\es\ies\dao\AlumnoDAOImpl.java#18)

```java
  public List<Alumno> buscarAlumno(Connection con, int edadMin, int edadMax) throws SQLException {
    String sql = "SELECT id, nombre, edad, ciclo FROM alumnos WHERE edad >= ? AND edad <= ? ORDER BY edad DESC, id ASC";
    List<Alumno> alumnos = new ArrayList<>();

    try (PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, edadMin);
      ps.setInt(2, edadMax);

    {...}
    } {...}
  }
```

Se enlaza como enteros, pues una edad es un número entero.
Devuelve un `ResultSet` con un id, nombre, edad y curso, ordenados por edad de manera descendente y en caso de haber iguales, por id de manera ascendente.

### RA2.2 (1 punto). Si el primer `INSERT` abrió una conexión distinta y se confirmó allí, ¿qué quedaría guardado al ejecutar rollback en la segunda conexión? Indica cómo evita tu DAO ese resultado

[Referencia](..\src\main\java\es\ies\dao\AlumnoDAOImpl.java#52)

```java
  public void insertarAlumnos(Connection con, List<Solicitud> solicitudes) throws SQLException { ... }
```

Quedaría guardado el primer insert antes de hacer rollback si se hizo `commit` o se mantuvo `setAutoCommit(true)`. mi código evita eso haciendo commit al final, así o se aplican los dos insert o ninguno.
