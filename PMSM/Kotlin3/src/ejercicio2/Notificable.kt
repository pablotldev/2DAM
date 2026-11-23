package ejercicio2

interface Notificable {
    abstract val prioridade: String
    fun enviar(mensaje: String)
    fun mostrarLog(mensaje: String) {
        println("[LOG ${prioridade}] ${mensaje}")
    }
}