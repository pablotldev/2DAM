fun main() {

    println("Es par (2)? ${evaluarNumero(2, { n: Int -> n % 2 == 0 })}")
    println("Es par (3)? ${evaluarNumero(3, { n: Int -> n % 2 == 0 })}")
    println("Es mayor que 100 (99)? ${evaluarNumero(99, { n: Int -> n > 100 })}")
    println("Es mayor que 100 (101)? ${evaluarNumero(101, { n: Int -> n > 100 })}")

}

fun evaluarNumero(num: Int, condicion: (Int) -> Boolean): String = if (condicion(num) == true) "Cumple la condición" else "No cumple la condición"