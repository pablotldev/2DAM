package es.instituto.db;

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
    try (InputStream iS = InitializeDB.class.getResourceAsStream("/db/schema.sql")) {

      if (iS == null) {
        throw new IOException("No existe el fichero en /resources/db/schema.sql");
      }

      String script = new String(iS.readAllBytes(), StandardCharsets.UTF_8);
      try (Connection conn = ConnectionDB.getServerConnection();
          Statement stmt = conn.createStatement()) {
        for (String line : script.split(";")) {
          if (!line.isBlank()) {
            stmt.execute(line.trim());
          }
        }
      }

    }
  }

}
