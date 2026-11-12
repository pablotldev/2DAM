package es.instituto.pregunta2.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitializeDB {

  private InitializeDB() {
  }

  public static void initialize() throws IOException, SQLException {
    try (InputStream is = InitializeDB.class.getResourceAsStream("/db/schema.sql")) {

      if (is == null) {
        throw new IOException("No existe el fichero en /resources/db/schema.sql");
      }

      String script = new String(is.readAllBytes(), StandardCharsets.UTF_8);
      try (Connection con = ConnectionDB.getServerConnection();
          Statement stmt = con.createStatement()) {

        for (String str : script.split(";")) {
          if (!str.isBlank()) {
            stmt.execute(str.trim());
          }
        }
      }

    }
  }

}
