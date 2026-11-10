package es.instituto.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.instituto.db.AlumnoDAO;
import es.instituto.models.Alumno;
import es.instituto.utils.Colors;
import es.instituto.utils.Input;

public class XMLAlumnos {

  private static final Path DEFECTO = Path.of("data", "alumnos.xml");

  private XMLAlumnos() {
  }

  public static void guardar(BufferedReader reader, AlumnoDAO dao) throws IOException {
    Path ruta = Input.leerRuta(reader, "Ruta del fichero XML", DEFECTO);

    try {
      List<Alumno> alumnos = dao.listar();

      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

      Element raiz = doc.createElement("alumnos");
      doc.appendChild(raiz);

      for (Alumno a : alumnos) {
        Element alumno = doc.createElement("alumno");
        raiz.appendChild(alumno);

        alumno.appendChild(nodo(doc, "nia", String.valueOf(a.nia())));
        alumno.appendChild(nodo(doc, "nombre", a.nombre()));
        alumno.appendChild(nodo(doc, "apellidos", a.apellidos()));
        alumno.appendChild(nodo(doc, "genero", a.genero()));
        alumno.appendChild(nodo(doc, "fechaNacimiento", a.fechaNacimiento().toString()));
        alumno.appendChild(nodo(doc, "ciclo", a.ciclo()));
        alumno.appendChild(nodo(doc, "curso", a.curso()));
        alumno.appendChild(nodo(doc, "grupo", String.valueOf(a.grupo())));
      }

      Path padre = ruta.toAbsolutePath().getParent();
      if (padre != null)
        Files.createDirectories(padre);

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      try (OutputStream os = Files.newOutputStream(ruta)) {
        transformer.transform(new DOMSource(doc), new StreamResult(os));
      }

      System.out.printf("%sGuardado(s) %d alumno(s) en %s.%n", Colors.GREEN, alumnos.size(), ruta.toAbsolutePath());

    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD: " + e.getMessage());
    } catch (ParserConfigurationException | TransformerException ex) {
      System.err.println(Colors.RED + "Error al generar el XML: " + ex.getMessage());
    }
  }

  public static void leer(BufferedReader reader, AlumnoDAO dao) throws IOException {
    Path ruta = Input.leerRuta(reader, "Ruta del fichero XML", DEFECTO);

    if (!Files.isRegularFile(ruta)) {
      System.out.printf("%sNo existe el fichero %s.", Colors.RED, ruta.toAbsolutePath());
      return;
    }

    List<Alumno> alumnos = new ArrayList<>();
    List<String> errores = new ArrayList<>();

    try {
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ruta.toFile());

      NodeList nodos = doc.getElementsByTagName("alumno");

      for (int i = 0; i < nodos.getLength(); i++) {
        Element alumno = (Element) nodos.item(i);

        try {
          String grupo = texto(alumno, "grupo");

          if (grupo.length() != 1)
            throw new IllegalArgumentException("grupo inválido");

          alumnos.add(new Alumno(
              Integer.parseInt(texto(alumno, "nia")),
              texto(alumno, "nombre"),
              texto(alumno, "apellidos"),
              texto(alumno, "genero"),
              LocalDate.parse(texto(alumno, "fechaNacimiento")),
              texto(alumno, "ciclo"),
              texto(alumno, "curso"),
              grupo.charAt(0)));
        } catch (IllegalArgumentException | DateTimeParseException e) {
          errores.add("Alumno " + (i + 1) + ": " + e.getMessage());
        }
      }
    } catch (ParserConfigurationException | SAXException e) {
      System.err.println(Colors.RED + "XML inválido: " + e.getMessage());
      return;
    }

    errores.forEach(error -> System.out.println(Colors.YELLOW + error));

    if (alumnos.isEmpty()) {
      System.out.println(Colors.RED + "No hay alumnos válidos que importar");
      return;
    }

    try {
      int total = dao.insertLote(alumnos);
      System.out.printf("%sImportado(s) %d alumno(s) desde %s.%n", Colors.GREEN, total, ruta.toAbsolutePath());
    } catch (SQLException e) {
      System.err.println(Colors.RED + "Error de BD al importar: " + e.getMessage());
    }
  }

  private static Element nodo(Document doc, String tag, String val) {
    Element elemento = doc.createElement(tag);
    elemento.setTextContent(val);
    return elemento;
  }

  private static String texto(Element padre, String tag) {
    NodeList nodos = padre.getElementsByTagName(tag);

    if (nodos.getLength() == 0) {
      throw new IllegalArgumentException("falta <" + tag + ">");
    }

    String val = nodos.item(0).getTextContent().trim();

    if (val.isEmpty()) {
      throw new IllegalArgumentException("<" + tag + "> está vacío");
    }
    return val;
  }

}
