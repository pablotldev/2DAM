package es.instituto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

  private static final Path P = Path.of("persona.json");

  public static void main(String[] args) {

    Gson gson = new Gson();

    try (FileWriter w = new FileWriter(P.toFile())) {

      List<Persona> personas = new ArrayList<>();
      personas.add(new Persona("Pablo", 21));
      personas.add(new Persona("Paloma", 21));
      personas.add(new Persona("Juan", 22));
      personas.add(new Persona("Yesenia", 22));
      personas.add(new Persona("Unai", 21));

      gson.toJson(personas, w);
    } catch (IOException e) {
      System.err.println("Error en la escritura del archivo JSON: " + e.getMessage());
    }

    Type tipoLista = new TypeToken<List<Persona>>() {
    }.getType();
    try (FileReader r = new FileReader(P.toFile())) {
      List<Persona> leidas = gson.fromJson(r, tipoLista);

      for (Persona pe : leidas) {
        System.out.println(pe);
      }

    } catch (FileNotFoundException e) {
      System.err.println("No se encuentra el archivo " + P.toString() + " :" + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error de lectura del fichero JSON: " + e.getMessage());
    }

  }
}