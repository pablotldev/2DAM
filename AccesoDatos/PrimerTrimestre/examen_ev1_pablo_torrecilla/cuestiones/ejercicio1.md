# Respuestas del ejercicio 1

## Pablo Torrecilla

### RA1.1 (1 punto). Señala la llamada con la que recuperas el atributo ciclo. ¿Dónde queda la solicitud con ciclo ASIR y por qué?

[Referencia](..\src\main\java\es\ies\classes\Ejercicio1.java#59)

```java
for (Solicitud s : ls) {
      count++;
      {...}

      if (!List.of("daw", "dam").contains(s.getCiclo().toLowerCase())) {
        System.out.println(Colors.RED + "Posición " + Colors.PURPLE + pos + Colors.RED
            + ". Ciclo " + Colors.PURPLE + s.getCiclo() + Colors.RED + " no es válido. Debe ser DAW o DAM");
        continue;
      }
      s.setCiclo(s.getCiclo().trim().toUpperCase());

      solicitudes.add(s);
    }
```

El if comprueba si el ciclo no es ni `daw` ni `dam`, y al cumplirse con `asir`, muestra un mensaje de error (El `System.out.println(...)`) y continúa a la siguiente iteración con el `continue;`.

### RA1.2 (1 punto). Con los dos CSV previos, quita `</matriculas>` del XML. ¿En qué operación falla tu programa y cómo compruebas que ambos CSV siguen intactos?

[Referencia](..\src\main\java\es\ies\classes\Ejercicio1.java)

```java
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
```

En el try-catch del main compruebo si el mensaje de error contiene la palabra `EOF`, y si la contiene muestro un mensaje de error distinto a si no lo tiene, que entonces sería un error de Entrada/Salida normal
