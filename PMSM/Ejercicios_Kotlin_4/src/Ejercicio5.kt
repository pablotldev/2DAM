fun main() {

    println("Con \"!!!\": ${formatearTexto("!!!", {})}")

}


fun formatearTexto(texto: String, formato: (String) -> String): String = formato(texto)