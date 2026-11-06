import utils.Colors

class Estudiante(nombreInicial: String, notaBase: Double) {

    var nombre: String = nombreInicial
        get() = field.uppercase().trim()
        set(value) {
            if (value.isEmpty()) println("${Colors.RED}Introduce un valor") else field = value
         }
}

fun main() {
    val es = Estudiante("Pablo", 10.0)
    println("${Colors.GREEN}Estudiante nombre: ${Colors.PURPLE}${es.nombre}")
    es.nombre = "Paco"
    println("${Colors.GREEN}Estudiante nombre: ${Colors.PURPLE}${es.nombre}")
    es.nombre = ""
    println("${Colors.GREEN}Estudiante nombre: ${Colors.PURPLE}${es.nombre}")
}