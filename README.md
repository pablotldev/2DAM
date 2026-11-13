# 2DAM

## Repositorio para el seguimiento de las tareas de clase/casa

Aquí está todo el material de **2º de DAM (Desarrollo de Aplicaciones Multiplataforma)**.

Cada carpeta corresponde a una asignatura y dentro están las prácticas separadas por trimestre y semana.

| Asignatura                           | Carpeta                                          | Contenido                                                        |
| ------------------------------------ | ------------------------------------------------ | ---------------------------------------------------------------- |
| Programación de Servicios y Procesos | [`PSP/`](PSP/)                                   | Java: hilos, ficheros, POO, actividad 1.3 (productor/consumidor) |
| Acceso a Datos                       | [`AccesoDatos/`](AccesoDatos/)                   | Java: ficheros, CSV, binarios, serialización, XML y JSON         |
| Desarrollo de Interfaces             | [`DesarrolloInterfaces/`](DesarrolloInterfaces/) | Maven + JavaFX                                                   |
| Móviles                              | [`Moviles/`](Moviles/)                           | Kotlin + Gradle                                                  |

---

## Estructura

```txt
2DAM/
├── AccesoDatos/              Acceso a datos (1er trimestre completo)
│   └── PrimerTrimestre/
│       ├── EjerciciosFile/   Semanas 2, 3 y 4 + opcionales
│       │   └── src/
│       │       ├── semana2/      Ejercicios de sistemas de ficheros
│       │       ├── semana3/      Ficheros de texto y CSV
│       │       ├── semana4/      Ficheros binarios y serialización
│       │       └── opcionales_semana3/
│       └── Semana5/         Ejercicios de XML y JSON
├── DesarrolloInterfaces/     Proyectos Maven con JavaFX
│   ├── actividad1javafx/     Actividad 1
│   ├── gestortareas/         Gestor de tareas (JavaFX + FXML)
│   ├── HolaJavaFX/           Ejemplo de Hola Mundo
│   └── holajavafx2/          Variante con FXML y JUnit
├── Moviles/
│   └── ejercicios/          Proyecto Gradle con Kotlin
├── PSP/                      Programación de servicios y procesos
│   ├── PrimerTrimestre/
│   │   └── src/              actividad1, ejemplos, ejerciciosPOO3, primerProyecto
│   └── Torrecilla_Pablo_A1_3/  Actividad 1.3 (hilos, javadoc en docs/)
├── elPS1.txt                 Perfil de PowerShell con atajos
└── .vscode/                  Configuración y tarea de javadoc compartida
```

---

## Requisitos

| Herramienta        | Versión       | Para qué                              |
| ------------------ | ------------- | ------------------------------------- |
| JDK                | 21 o superior | Todas las asignaturas                 |
| Maven              | 3.9+          | Desarrollo de Interfaces              |
| Gradle             | 9.2 (wrapper) | Móviles                               |
| Visual Studio Code | —             | Editor, con _Extension Pack for Java_ |
| PowerShell 7       | —             | Atajos de `elPS1.txt`                 |

El JDK 25 lo usan los proyectos de JavaFX. `actividad1javafx` se queda en JavaFX 21 por compatibilidad.

---

## Cómo ejecutar cada proyecto

### Acceso a datos y PSP

Son ejercicios sueltos, sin build system. Se compilan y ejecutan desde VS Code (botón _Run_ sobre el fichero) o a mano:

```powershell
cd AccesoDatos\PrimerTrimestre\EjerciciosFile\src
javac semana3\Ejercicio1.java
java semana3\Ejercicio1
```

Ojo: algunos ejercicios leen ficheros con rutas relativas (`alumnos.csv`, `path.txt`, `mensaje.txt`). Hay que ejecutarlos desde la carpeta que contiene esos ficheros o ajustar la ruta en el código.

Los ficheros .pdf son los enunciados de cada semana.

### Desarrollo de Interfaces (Maven)

```powershell
cd DesarrolloInterfaces\gestortareas
mvn clean compile        # o el atajo: mcc
mvn javafx:run          # o el atajo: mjr
mvn test                # solo holajavafx2, que tiene JUnit
```

Cada proyecto declara su `mainClass` en el `javafx-maven-plugin`:

| Proyecto           | Main                                     | JavaFX |
| ------------------ | ---------------------------------------- | ------ |
| `actividad1javafx` | `es.ieslazarocardenas.di.actividad1.App` | 21     |
| `gestortareas`     | `es.ies.gestortareas.App`                | 25     |
| `HolaJavaFX`       | `es.ies.javafx.App`                      | 25     |
| `holajavafx2`      | `es.ies.javafx.App`                      | 25     |

### Móviles (Gradle + Kotlin)

```powershell
cd Moviles\ejercicios
.\gradlew.bat run       # mainClass = Ejercicio1Kt
.\gradlew.bat build
```

El módulo `app` usa Kotlin 2.2.21, toolchain JVM 21, Guava y JUnit 5.

---

## Javadoc

La tarea **Generar Javadoc HTML** genera la documentación HTML de un proyecto y la deja en `docs/`.

Copia `.vscode/tasks.json` en la carpeta `.vscode` del proyecto que quieras documentar y ejecuta la tarea. Requiere PowerShell.

En `PSP/Torrecilla_Pablo_A1_3/docs/` ya está la generada, ahí se explica el por qué de cada método.

---

## Atajos de PowerShell (`elPS1.txt`)

Perfil que define atajos para moverse y compilar más rápido:

```powershell
. .\elPS1.txt

ad      # ir a AccesoDatos
psp     # ir a PSP
di      # ir a DesarrolloInterfaces
dam     # ir a la raíz del repo

c dam   # abrir VS Code en una carpeta (alias: ad, psp, di, dam, g)
m       # atajo de mvn
mcc     # mvn clean compile
mjr     # mvn javafx:run
mdt     # mvn dependency:tree
mt      # mvn test
p       # abrir el proyecto  de Móviles y arrancarlo
```

Las rutas están en `$HOME\Documents\2DAM`, hay que cambiarlas si el repo está en otro sitio.

---

## Notas del repositorio

- `.gitignore` ignora `bin/`, `test-classes/`, `*.jar` y los comprimidos (`*.zip`, `*.rar`, `*.7z`). Los `.jar` del wrapper de Gradle están ya subidos, así que no se borran.
- Hay folders de `target/` y `build/` versionados por error; se pueden borrar del índice cuando quieras.
- `.github/modernize/java-upgrade/` contiene los hooks de la herramienta de actualización de Java.
- En `PSP/Torrecilla_Pablo_A1_3/` se usó IA (Claude, Gemini) para depurar y resolver dudas sobre el código.

---

<small><center>Creado por Pablo Torrecilla</center></small>
