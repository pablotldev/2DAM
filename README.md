# 2DAM

Repositorio de seguimiento de las prácticas de **2º de DAM (Desarrollo de Aplicaciones Multiplataforma)**.

Cada carpeta corresponde a una asignatura. Dentro, el material está separado por trimestre y por práctica. Ahora mismo solo hay contenido del **primer trimestre**, que sigue en curso.

> Última revisión del README: **21/11/2026**.

---

## Asignaturas

| Asignatura                                                   | Carpeta                                          | Contenido                                                                                          |
| ------------------------------------------------------------ | ------------------------------------------------ | -------------------------------------------------------------------------------------------------- |
| Acceso a Datos                                               | [`AccesoDatos/`](AccesoDatos/)                   | Ficheros, CSV, binarios, serialización, XML/XSLT, JSON, JDBC/MySQL, simulacros y examen de la 1ª evaluación |
| Programación de Servicios y Procesos                         | [`PSP/`](PSP/)                                   | Repaso de Java, hilos, procesos, `ProcessBuilder`, interoperabilidad con Rust y actividad 1.3      |
| Desarrollo de Interfaces                                     | [`DesarrolloInterfaces/`](DesarrolloInterfaces/) | Cuatro proyectos Maven con JavaFX                                                                  |
| Programación Multimedia y Dispositivos Móviles (PMDM)        | [`PMSM/`](PMSM/)                                 | Kotlin: clases, encapsulación, herencia, interfaces y proyectos Gradle                            |

> La asignatura de móviles aparece en el repositorio como `PMSM/`, aunque en los commits y en el IDE se usa el nombre `PMDM`.

---

## Estructura

```txt
2DAM/
├── AccesoDatos/
│   ├── README.md
│   └── PrimerTrimestre/
│       ├── EjerciciosFile/              Semanas 2, 3 y 4 + opcionales (Java sin build system)
│       │   └── src/
│       │       ├── semana2/             Sistemas de ficheros
│       │       ├── semana3/             Ficheros de texto y CSV
│       │       ├── semana4/             Ficheros binarios y serialización
│       │       └── opcionales_semana3/
│       ├── Semana5/                     XML, XSLT y JSON
│       ├── JDBC_Pablo_Torrecilla/       JDBC 1: primeras consultas
│       ├── JDBC2_Pablo_Torrecilla/      JDBC 2: CRUD con parámetros
│       ├── JDBC3_Pablo_Torrecilla/      JDBC 3: transacciones y rollback
│       ├── jdbc4_pablo_torrecilla/      JDBC 4: esqueleto del proyecto
│       ├── JDBC11_Pablo_Torrecilla/     Tarea 11: DAO + CSV/XML
│       ├── practica_json/               Gson: lectura y escritura de JSON
│       ├── simulacro_examen/            Simulacro de examen
│       ├── sim_examen/                  Segundo simulacro (DAO + CSV)
│       └── examen_ev1_pablo_torrecilla/ Examen de la 1ª evaluación
├── DesarrolloInterfaces/
│   ├── actividad1javafx/                Actividad 1 (JavaFX 21)
│   ├── gestortareas/                    Gestor de tareas (JavaFX 25)
│   ├── HolaJavaFX/                      Ejemplo Hola Mundo (JavaFX 25)
│   └── holajavafx2/                     Variante con JUnit 4
├── PMSM/                                PMDM: Kotlin
│   ├── Kotlin2/                         Ejercicios de clases y objetos
│   ├── Kotlin2_2/                       Getters, setters y encapsulación
│   ├── Kotlin3/                         Herencia e interfaces
│   ├── ejercicios/                      Proyecto Gradle (Kotlin 2.2.21, JVM 21)
│   └── prueba/                          Proyecto Gradle de pruebas (Kotlin 2.3.20, JVM 25)
├── PSP/
│   └── Primer Trimestre/
│       ├── RepasoJava/                  POO, herencia y primeros procesos
│       ├── ProgramacionMultihilo/       Ejercicio Tic/Tac con hilos
│       ├── HilosRaton/                  Ratón: versión con hilo y versión multihilo
│       ├── EjerciciosProcesosI/         ProcessBuilder + binarios en Rust (Cargo)
│       ├── EjerciciosProcesosII/        Ejercicios de procesos (en curso)
│       └── Torrecilla_Pablo_A1_3/       Actividad 1.3 + Javadoc generado
├── .github/modernize/java-upgrade/      Hooks de la herramienta de actualización de Java
├── .vscode/                             Configuración y tarea para generar Javadoc
├── .gitignore
├── elPS1.ps1                            Perfil de PowerShell con atajos
└── README.md
```

---

## Requisitos

| Herramienta                          | Versión                    | Para qué                                                                 |
| ------------------------------------ | -------------------------- | ------------------------------------------------------------------------ |
| JDK                                  | 21 o superior              | Acceso a Datos, PSP y JavaFX. Los proyectos JavaFX 25 necesitan JDK 25.  |
| Maven                                | 3.9 o superior             | Proyectos Maven de Acceso a Datos y Desarrollo de Interfaces             |
| Gradle                               | 9.2+ (wrapper)             | `PMSM/ejercicios` y `PMSM/prueba`                                        |
| Rust                                 | Estable, edition 2024      | `PSP/Primer Trimestre/EjerciciosProcesosI`                               |
| MySQL o MariaDB                      | MySQL 8+ / MariaDB 10+     | Prácticas JDBC de Acceso a Datos                                         |
| Visual Studio Code                   | —                          | Ejercicios Java sin build system y tarea de Javadoc                      |
| IntelliJ IDEA                        | —                          | Módulos Kotlin de `PMSM/`                                                |
| PowerShell 7                         | —                          | `elPS1.ps1` y la tarea de Javadoc                                        |

Detalle de versiones de Java:

- `actividad1javafx` compila con Java 21 y JavaFX 21.
- `gestortareas`, `HolaJavaFX` y `holajavafx2` compilan con Java 25 y JavaFX 25.
- `PMSM/ejercicios` usa toolchain JVM 21.
- `PMSM/prueba` usa toolchain JVM 25.

---

## Cómo empezar

```powershell
git clone https://github.com/Pakete-123/2DAM.git
cd 2DAM
```

---

## Acceso a Datos

### Ejercicios sin Maven

Los ejercicios de `EjerciciosFile` y `Semana5` se compilan y ejecutan a mano o con el botón _Run_ de VS Code.

`EjerciciosFile` lee varios ficheros con rutas relativas (`src/mensaje.txt`, `src/alumnos.csv`, etc.), así que hay que ejecutarlo desde la raíz de `EjerciciosFile`:

```powershell
cd AccesoDatos\PrimerTrimestre\EjerciciosFile
javac src\semana3\Ejercicio1.java
java -cp src semana3.Ejercicio1
```

Los ejercicios de `semana2` usan rutas como `datos/alumnos.txt`. Si esa carpeta no existe, hay que crearla o ajustar la ruta en el código.

`Semana5` lee `catalogo_libros.xml` desde la raíz de la carpeta:

```powershell
cd AccesoDatos\PrimerTrimestre\Semana5
javac src\ejercicio1\Ejercicio1.java src\ejercicio5\Ejercicio5.java
java -cp src ejercicio1.Ejercicio1
java -cp src ejercicio5.Ejercicio5
```

Los PDF de cada carpeta son los enunciados de los ejercicios.

### Proyectos Maven y JDBC

Cada proyecto JDBC es un proyecto Maven independiente. Ejemplo con `JDBC2`:

```powershell
cd AccesoDatos\PrimerTrimestre\JDBC2_Pablo_Torrecilla
mvn clean compile
mvn exec:java -Dexec.mainClass="es.instituto.Main"
```

Cambia el `mainClass` según el proyecto (`es.ies.Main`, `es.instituto.pregunta1.Main`, etc.). También se puede abrir el `Main.java` del proyecto y ejecutarlo desde VS Code.

Los proyectos que conectan con MySQL leen la configuración de un fichero `.env`. En la mayoría hay una plantilla llamada `.copy.env`:

```powershell
Copy-Item .copy.env .env
```

Después hay que ajustar los valores de conexión. Las variables más habituales son:

```env
DB_URL=jdbc:mysql://localhost:3306/instituto
DB_USER=usuario
DB_PASSWORD=contraseña
```

Algunos proyectos usan además `DB_SERVER` o `DB_URL_SERVER` para crear la base de datos. Revisa siempre el `ConnectionDB.java` del proyecto antes de arrancarlo.

> `.env` está ignorado por Git. No se sube al repositorio.

---

## Programación de Servicios y Procesos

### Java

Los ejercicios de `RepasoJava`, `ProgramacionMultihilo`, `HilosRaton`, `EjerciciosProcesosI` y `EjerciciosProcesosII` son Java sin build system. Se ejecutan desde VS Code o compilándolos a mano.

### Rust

`EjerciciosProcesosI` incluye dos binarios de Rust definidos en `Cargo.toml`:

```powershell
cd "PSP\Primer Trimestre\EjerciciosProcesosI"
cargo build --release
cargo run --bin random
cargo run --bin reader
```

Los lanzadores `Launcher_rs` compilan el binario y ejecutan `target\release\random.exe` o `target\release\reader.exe`. Necesitan `cargo` en el `PATH` y deben ejecutarse desde la raíz del proyecto.

### Actividad 1.3

`Torrecilla_Pablo_A1_3` contiene la actividad de hilos y la documentación Javadoc generada en `docs/`.

---

## Desarrollo de Interfaces

Todos los proyectos son Maven:

```powershell
cd DesarrolloInterfaces\gestortareas
mvn clean compile
mvn javafx:run
```

De los cuatro proyectos, solo `holajavafx2` tiene tests (JUnit 4):

```powershell
cd ..\holajavafx2
mvn test
```

Cada proyecto declara su clase principal en el `javafx-maven-plugin`:

| Proyecto           | Main                                     | JavaFX |
| ------------------ | ---------------------------------------- | ------ |
| `actividad1javafx` | `es.ieslazarocardenas.di.actividad1.App` | 21     |
| `gestortareas`     | `es.ies.gestortareas.App`                | 25     |
| `HolaJavaFX`       | `es.ies.javafx.App`                      | 25     |
| `holajavafx2`      | `es.ies.javafx.App`                      | 25     |

---

## PMSM (Kotlin)

`PMSM/ejercicios` es un proyecto Gradle con el módulo `app`:

```powershell
cd PMSM\ejercicios
.\gradlew.bat run
.\gradlew.bat build
```

La clase principal configurada es `Ejercicio1Kt`. El resto de ejercicios se pueden ejecutar desde IntelliJ IDEA.

`Kotlin2`, `Kotlin2_2` y `Kotlin3` son módulos de IntelliJ sin Gradle. Se abren como proyecto y se ejecuta el `main` de cada fichero.

`PMSM/prueba` es un proyecto Gradle vacío para hacer pruebas sueltas.

> Si el wrapper de Gradle falla porque falta `gradle-wrapper.jar`, ejecuta `gradle wrapper` en la carpeta del proyecto o ábrelo con IntelliJ IDEA.

---

## Javadoc

La tarea **Generar Javadoc HTML** genera la documentación de un proyecto y la deja en `docs/`.

1. Copia `.vscode/tasks.json` en la carpeta `.vscode` del proyecto que quieras documentar.
2. Ejecuta la tarea **Generar Javadoc HTML** desde VS Code.

Requiere PowerShell. En `PSP/Primer Trimestre/Torrecilla_Pablo_A1_3/docs/` ya está generada la documentación de la actividad 1.3.

---

## Atajos de PowerShell (`elPS1.ps1`)

`elPS1.ps1` es un fragmento de perfil que define atajos para moverse por el repositorio y compilar más rápido. Se puede copiar en `$PROFILE` o cargar en la sesión actual:

```powershell
. .\elPS1.ps1
```

Atajos disponibles:

```powershell
ad      # ir a AccesoDatos
psp     # ir a PSP
di      # ir a DesarrolloInterfaces
dam     # ir a la raíz del repo
g       # ir a gachamon
rl      # ir a rust_learn

c dam   # abrir VS Code en una carpeta (acepta ad, psp, di, dam, g, rl)
m       # atajo de mvn
mcc     # mvn clean compile
mjr     # mvn javafx:run
mdt     # mvn dependency:tree
mt      # mvn test
mvd     # mvn versions:display-dependency-updates
mvu     # mvn versions:use-latest-releases
p       # abrir gachamon y arrancar pnpm dev
```

Las rutas están definidas para `$HOME\Documents\2DAM`. Si el repositorio está en otra ruta, hay que cambiar `$global:Atajos` al principio de `elPS1.ps1`.

El script también carga `oh-my-posh` y el módulo `Terminal-Icons`; si no los tienes instalados, comenta esas dos líneas.

---

## Notas del repositorio

- `.gitignore` ignora `bin/`, `test-classes/`, `*.jar`, los comprimidos (`*.zip`, `*.rar`, `*.7z`), `.env` y `target/`.
- Quedan algunos archivos de `target/` versionados de commits antiguos. `target/` ya está ignorado, así que se pueden quitar del índice cuando se quiera.
- El `gradle-wrapper.jar` de `PMSM/ejercicios` está ignorado por la regla `*.jar`. El de `PMSM/prueba` sí está versionado.
- `.github/modernize/java-upgrade/` contiene los hooks de la herramienta de actualización de Java.
- En `PSP/Primer Trimestre/Torrecilla_Pablo_A1_3/` se usó IA (Claude, Gemini) para depurar y resolver dudas sobre el código.
- No todos los ejercicios están terminados: hay prácticas en curso y algunos `Main` son esqueletos.

---

<small><center>Creado por Pablo Torrecilla</center></small>
