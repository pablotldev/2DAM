import utils.Colors

class Termostato(temperatura: Double = 20.0) {
    var temp: Double = temperatura
        set(value) {
            if (value in -10.0..45.0) field = value else println(Colors.RED + "Fuera del rango (-10 - 45)")
        }
}

fun main() {
    val term1 = Termostato()
    val term2 = Termostato(15.0)
    println("${Colors.GREEN}Temp 1: ${Colors.PURPLE + term1.temp + Colors.WHITE} - ${Colors.GREEN}Temp 2: ${Colors.PURPLE + term2.temp}")
}