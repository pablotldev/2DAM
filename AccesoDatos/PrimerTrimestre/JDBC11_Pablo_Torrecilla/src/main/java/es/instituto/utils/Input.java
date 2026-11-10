package es.instituto.utils;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class Input {

  public static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
      .withResolverStyle(ResolverStyle.STRICT);

  private static final String REGEX_TEXTO = "[\\p{L}]+(?:[ '\\-][\\p{L}]+)*";
  private static final String REGEX_PALABRA = "[\\p{L}]+";

  private Input() {
  }

  public static int leerNia(BufferedReader reader) throws IOException {
    return leerEntero(reader, "NIA: ", "NIA inválido. Debe ser un número mayor que 0: ", 1, Integer.MAX_VALUE);
  }

  public static String leerNombre(BufferedReader reader) throws IOException {
    return leerTexto(reader, "Nombre: ", "Nombre inválido. Solo letras, espacios, apóstrofo o guión (máx 40): ",
        REGEX_TEXTO, 40);
  }

  public static String leerApellidos(BufferedReader reader) throws IOException {
    return leerTexto(reader, "Apellidos: ", "Apellidos inválidos. Solo letras, espacios, apóstrofo o guión (máx 70): ",
        REGEX_TEXTO, 70);
  }

  public static String leerGenero(BufferedReader reader) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + "Género (Masculino/Femenino): " + Colors.WHITE);
      String valor = leerLinea(reader).toLowerCase(Locale.ROOT);

      switch (valor) {
        case "masculino", "m" -> {
          return "Masculino";
        }
        case "femenino", "f" -> {
          return "Femenino";
        }
        default -> System.out.println(Colors.RED + "Género inválido.");
      }
    }
  }

  public static LocalDate leerFechaNacimiento(BufferedReader reader) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + "Fecha de nacimiento (dd/MM/aaaa): " + Colors.WHITE);
      String valor = leerLinea(reader);

      try {
        LocalDate fecha = LocalDate.parse(valor, FECHA);
        if (fecha.isAfter(LocalDate.now())) {
          System.out.println(Colors.RED + "La fecha no puede ser futura.");
          continue;
        }
        if (fecha.isBefore(LocalDate.now().minusYears(120))) {
          System.out.println(Colors.RED + "La fecha no puede ser anterior a 120 años");
          continue;
        }
        return fecha;
      } catch (DateTimeParseException e) {
        System.out.println(Colors.RED + "Fecha inválida. Usa dd/MM/aaaa.");
      }
    }
  }

  public static String leerCiclo(BufferedReader reader) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + "Ciclo (DAM/DAW): " + Colors.WHITE);
      String valor = leerLinea(reader).toUpperCase(Locale.ROOT);

      if (valor.equals("DAM") || valor.equals("DAW")) {
        return valor;
      }
      System.out.println(Colors.RED + "Ciclo inválido. Escribe DAM o DAW." + Colors.RESET);
    }
  }

  public static String leerCurso(BufferedReader reader) throws IOException {
    return String.valueOf(leerEntero(reader,
        "Curso (1/2): ",
        "Curso inválido. Escribe 1 o 2: ",
        1, 2));
  }

  public static char leerGrupo(BufferedReader reader) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + "Grupo (A-Z): " + Colors.WHITE);
      String valor = leerLinea(reader).toUpperCase(Locale.ROOT);

      if (valor.matches("[A-Z]")) {
        return valor.charAt(0);
      }
      System.out.println(Colors.RED + "Grupo inválido. Debe ser una sola letra." + Colors.RESET);
    }
  }

  public static String leerPalabra(BufferedReader reader, String msg) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + msg + ": " + Colors.WHITE);
      String val = leerLinea(reader);

      if (val.matches(REGEX_PALABRA)) {
        return val;
      }
      System.out.println(Colors.RED + "Introduce una sola palabra, sin números ni símbolos.");
    }
  }

  public static int leerEntero(BufferedReader reader, String mensaje, String error, int min, int max)
      throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + mensaje + Colors.WHITE);
      String valor = leerLinea(reader);

      try {
        int numero = Integer.parseInt(valor);
        if (numero < min || numero > max) {
          System.out.println(Colors.RED + error + Colors.RESET);
          continue;
        }
        return numero;
      } catch (NumberFormatException e) {
        System.out.println(Colors.RED + error + Colors.RESET);
      }
    }
  }

  public static String leerTexto(BufferedReader reader, String mensaje, String error, String regex, int max)
      throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + mensaje + Colors.WHITE);
      String valor = leerLinea(reader).replaceAll("\\s+", " ");

      if (valor.length() <= max && valor.matches(regex)) {
        return valor;
      }
      System.out.println(Colors.RED + error + Colors.RESET);
    }
  }

  public static String leerLinea(BufferedReader reader) throws IOException {
    String linea = reader.readLine();
    if (linea == null) {
      throw new EOFException("Entrada finalizada.");
    }
    return linea.trim();
  }

  public static boolean confirm(BufferedReader reader, String msg) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + msg + " (s/n): " + Colors.WHITE);
      String val = leerLinea(reader).toLowerCase(Locale.ROOT);

      if (val.equals("s") || val.equals("si") || val.equals("sí")) {
        return true;
      }
      if (val.equals("n") || val.equals("no")) {
        return false;
      }
      System.out.println(Colors.RED + "Responde 's' o 'n'.");
    }
  }

  public static Path leerRuta(BufferedReader reader, String msg, Path def) throws IOException {
    while (true) {
      System.out.print(Colors.BLUE + msg + "[Enter = " + def + "]: " + Colors.WHITE);
      String val = leerLinea(reader);

      if (val.isBlank()) {
        return def;
      }
      try {
        return Path.of(val);
      } catch (InvalidPathException e) {
        System.out.println(Colors.RED + "Ruta inválida.");
      }
    }
  }

}
