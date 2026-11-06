fun main() {
    var apr: Int = 0
    var sus: Int = 0
    var notas: Int = 1

    println("Introduce 10 notas ")
    while (notas <= 10) {
        print("Nota $notas: ")
        val input = readlnOrNull()?.toDoubleOrNull() ?: -1.0
        if (input in 0.0 .. 10.0) {
            notas++
            if (input >= 7) {
                apr++
                continue
            }
            sus++
        }
    }
    println("Aprobados: $apr, Suspensos: $sus")
}