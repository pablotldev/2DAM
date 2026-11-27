fun main() {
    val notas = listOf(4.5, 7.0, 9.2, 3.8, 5.0, 6.4, 2.1)
    val aprobados = notas.filter { it >= 5 }
    val sobresalientes = aprobados.filter { it >= 9 }

    println(notas)
    println(aprobados)
    println(sobresalientes)
}