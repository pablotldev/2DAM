fun main() {

    println("Introduce un número entero entre 1 y 99 (ambos inclusive): ")
    var num: Int? = 0
    while (true) {
        num = readlnOrNull()?.toIntOrNull()
        if (num in 1..99) {
            break;
        }
        println("Introduce un número válido")
    }
    print("Dígitos del número: ${num.toString().length}")
}