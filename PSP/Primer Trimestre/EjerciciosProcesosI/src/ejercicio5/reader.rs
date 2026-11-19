use std::io::{self, BufRead, Write};

fn main() -> io::Result<()> {
  let entrada = io::stdin();
  let salida = io::stdout();
  let mut buffer_salida = salida.lock();

  for linea in entrada.lock().lines() {
    let texto = linea?;
    writeln!(buffer_salida, "{}", texto.to_uppercase())?;
    buffer_salida.flush()?;
  }

  Ok(())
}