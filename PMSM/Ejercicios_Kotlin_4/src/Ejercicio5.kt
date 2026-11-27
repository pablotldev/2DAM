fun main() {

    println("(miau) -> Con \"!!!\": ${formatearTexto("miau", {s -> s.uppercase() + "!!!"})}")
    println("(hola que TAL) -> Convirtiendo a snake_case: ${formatearTexto("hola que TAL", {s -> s.lowercase().replace(" ", "_")})}")
    println("(que pasa) -> 3 primeros caracteres: ${formatearTexto("que pasa", {s -> s.take(3)})}")

}


fun formatearTexto(texto: String, formato: (String) -> String): String = formato(texto)