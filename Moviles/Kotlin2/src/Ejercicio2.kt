import utils.Colors
import java.awt.Color

class Automovil(val marca: String, val anio: Int? = 2026) {
    var encendido: Boolean = false
    fun arrancar() {
        if (!encendido) {
            encendido = true
            println("${Colors.GREEN}Se ha encendido el ${Colors.PURPLE}$marca${Colors.GREEN} del año ${Colors.PURPLE}$anio${Colors.GREEN} se ha encendido")
        }
        println("${Colors.GREEN}El ${Colors.PURPLE}$marca$Colors.GREEN del año ${Colors.PURPLE}$anio$Colors.GREEN ya está encendido")
    }
}

fun main() {
    val v = Automovil("Koenigsegg Jesko Absolut", 2022)
    v.arrancar()
    v.arrancar()
}