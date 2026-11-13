package ejercicio2

import utils.Colors

interface Notificable {
    val prioridad: String
    fun enviar(mensaje: String)
    fun mostrarLog(mensaje: String) {
        println("${Colors.BLUE}[LOG ${Colors.PURPLE}$prioridad]: ${Colors.GREEN}$mensaje")
    }
}