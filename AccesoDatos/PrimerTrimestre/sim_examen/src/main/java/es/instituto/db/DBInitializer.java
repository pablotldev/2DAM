package es.instituto.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import es.instituto.utils.Colors;

public final class DBInitializer {

  private DBInitializer() {
  }

  public static void initialize() throws IOException, SQLException {

    try (InputStream is = DBInitializer.class.getResourceAsStream("/db/schema.sql")) {

      if (is == null) {
        System.out.println(Colors.RED + "No existe el fichero /db/schema.sql");
        System.exit(1);
      }

      String script = new String(is.readAllBytes(), StandardCharsets.UTF_8);

      try (Connection con = ConnectionDB.getServerConnection();
          Statement stmt = con.createStatement()) {

        for (String string : script.split(";")) {
          stmt.execute(string);
        }

      }

    }

  }
}
