package es.ies.classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import es.ies.models.Solicitud;
import es.ies.utils.Colors;

public class Ejercicio1 {

  private static final Path FILE_XML = Path.of("matriculas.xml");
  private static final Path DIR_CSV = Path.of("salida");

  private static final Path FILE_DAM_CSV = Path.of("salida", "dam.csv");
  private static final Path FILE_DAW_CSV = Path.of("salida", "daw.csv");

  private Ejercicio1() {
  }

  public static List<Solicitud> leerXML() throws IOException {
    List<Solicitud> solicitudes = new ArrayList<>();
    if (Files.notExists(FILE_XML)) {
      System.out.println(Colors.RED + "El fichero XML no existe.");
      return solicitudes;
    }

    XmlMapper mapper = new XmlMapper();

    List<Solicitud> ls = mapper.readValue(FILE_XML.toFile(), new TypeReference<List<Solicitud>>() {
    });

    int pos = 0;
    for (Solicitud s : ls) {
      pos++;

      if (s.getNombre().contains(";") || s.getNombre().length() > 60) {
        System.out.println(Colors.RED + "Posición " + Colors.PURPLE + pos + Colors.RED + " Nombre " + Colors.PURPLE
            + s.getNombre() + Colors.RED
            + ". no es válido. No puede contener ni ';' ni tener más de 60 caracteres");
        continue;
      }

      if (s.getEdad() < 1 || s.getEdad() > 120) {
        System.out.println(Colors.RED + "Posición " + Colors.PURPLE + pos + Colors.RED
            + ". Edad " + Colors.PURPLE + s.getEdad() + Colors.RED + ". Debe estar entre 1 y 120 años.");
        continue;
      }

      if (!List.of("daw", "dam").contains(s.getCiclo().toLowerCase())) {
        System.out.println(Colors.RED + "Posición " + Colors.PURPLE + pos + Colors.RED
            + ". Ciclo " + Colors.PURPLE + s.getCiclo() + Colors.RED + " no es válido. Debe ser DAW o DAM");
        continue;
      }
      s.setCiclo(s.getCiclo().trim().toUpperCase());

      solicitudes.add(s);
    }

    return solicitudes;
  }

  private static void crearCSV(List<Solicitud> solicitudes) {
    try {
      if (Files.notExists(DIR_CSV)) {
        Files.createDirectory(DIR_CSV);
      }
    } catch (IOException e) {
      System.err.println(Colors.RED + "No se pudo crear el directorio " + Colors.PURPLE + DIR_CSV + Colors.RED + ": "
          + e.getMessage());
    }

    String cabecera = "nombre;edad;ciclo";

    try (BufferedWriter bwM = new BufferedWriter(new FileWriter(FILE_DAM_CSV.toFile(), StandardCharsets.UTF_8, true));
        BufferedWriter bwW = new BufferedWriter(new FileWriter(FILE_DAW_CSV.toFile(), StandardCharsets.UTF_8, true))) {

      if (Files.notExists(FILE_DAM_CSV)) {
        bwM.write(cabecera);
        bwM.newLine();
      }
      if (Files.notExists(FILE_DAW_CSV)) {
        bwW.write(cabecera);
        bwW.newLine();
      }

      for (Solicitud s : solicitudes) {
        String cuerpo = s.getNombre() + ";" + s.getEdad() + ";" + s.getCiclo();
        if (s.getCiclo().equalsIgnoreCase("daw")) {
          bwW.write(cuerpo);
          bwW.newLine();
        } else if (s.getCiclo().equalsIgnoreCase("dam")) {
          bwM.write(cuerpo);
          bwM.newLine();
        } else {
          System.out.println(Colors.YELLOW + "No se ha escrito el alumno con el ciclo " + s.getCiclo());
        }
      }

    } catch (IOException e) {
      System.err.println(Colors.RED + "Error de escritura en los ficheros CSV: " + e.getMessage());
    }

  }

  public static void main() {

    try {
      List<Solicitud> solicitudes = leerXML();
      crearCSV(solicitudes);
    } catch (IOException e) {
      if (e.getMessage().contains("EOF")) {
        System.out.println(Colors.RED + "El fichero XML está incompleto: " + e.getMessage());
      } else {
        System.out.println(Colors.RED + "Error de entrada/salida: " + e.getMessage());
      }
    }

  }
}
