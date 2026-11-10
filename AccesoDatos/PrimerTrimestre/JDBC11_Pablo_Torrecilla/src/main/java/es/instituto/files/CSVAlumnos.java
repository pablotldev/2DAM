package es.instituto.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class CSVAlumnos {

  private static final Path DEFECTO = Path.of("data", "alumnos.csv");
  private static final String CABECERA = "NIA;Nombre;Apellidos;Genero;Fecha_nacimiento;Ciclo;Curso;Grupo";
  private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
      .withResolverStyle(ResolverStyle.STRICT);

  private CSVAlumnos() {
  }

  public static void guardar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    Path ruta = Input.leerRuta(reader, "Ruta del fichero CSV", DEFECTO);

    try {
      List<Alumno> alumnos = dao.listar();

      Path padre = ruta.toAbsolutePath().getParent();
      if (padre != null) {
        Files.createDirectories(padre);
      }

      try (BufferedWriter bw = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
        bw.write(CABECERA);
        bw.newLine();

        for (Alumno a : alumnos) {
          bw.write(String.join(";",
              String.valueOf(a.nia()),
              a.nombre(),
              a.apellidos(),
              a.genero(),
              FECHA.format(a.fechaNacimiento()),
              a.ciclo(),
              a.curso(),
              String.valueOf(a.grupo())));
          bw.newLine();
        }
      }

      System.out.printf("%sGuardados %d alumno(s) en %s.%n", Colors.GREEN, alumnos.size(), ruta.toAbsolutePath());

    } catch (SQLException e) {
      System.out.println(Colors.RED + "Error de BD: " + e.getMessage());
    }
  }

  public static void leer(BufferedReader reader, AlumnoDAO dao) throws IOException {
    Path ruta = Input.leerRuta(reader, "Ruta del fichero CSV", DEFECTO);

    if (!Files.isRegularFile(ruta)) {
      System.out.printf("%sNo existe el fichero %s.%n", Colors.RED, ruta.toAbsolutePath());
      return;
    }

    List<Alumno> alumnos = new ArrayList<>();
    List<String> errores = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
      String linea;
      int numero = 0;

      while ((linea = br.readLine()) != null) {
        numero++;

        if (linea.isBlank() || (numero == 1 && linea.startsWith("NIA;"))) {
          continue;
        }

        String[] partes = linea.split(";", -1);

        if (partes.length != 8) {
          errores.add("Línea " + numero + ": Se esperaban 8 columnas.");
          continue;
        }

        try {
          int nia = Integer.parseInt(partes[0].trim());
          String grupo = partes[7].trim();

          if (nia <= 0
              || partes[1].isBlank()
              || partes[2].isBlank()
              || partes[3].isBlank()
              || partes[4].isBlank()
              || partes[5].isBlank()
              || partes[6].isBlank()
              || grupo.length() != 1) {
            throw new IllegalArgumentException("datos obligatorios vacíos o inválidos");
          }

          alumnos.add(new Alumno(
              nia,
              partes[1].trim(),
              partes[2].trim(),
              partes[3].trim(),
              LocalDate.parse(partes[4].trim(), FECHA),
              partes[5].trim(),
              partes[6].trim(),
              grupo.charAt(0)));

        } catch (IllegalArgumentException | DateTimeParseException e) {
          errores.add("Línea " + numero + ": " + e.getMessage());
        }
      }
    }
    errores.forEach(error -> System.out.println(Colors.YELLOW + error));

    if (alumnos.isEmpty()) {
      System.out.println(Colors.RED + "No hay alumnos válidos que importar.");
      return;
    }

    try {
      int total = dao.insertLote(alumnos);
      System.out.printf("%sImportados %d alumno(s) desde %s.%n", Colors.GREEN, total, ruta.toAbsolutePath());
    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD al importar: " + e.getMessage());
    }
  }

}
