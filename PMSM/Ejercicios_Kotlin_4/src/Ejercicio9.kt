class Tarea(val descripcion: String, val prioridad: Int, var urgente: Boolean = false) {
    override fun toString(): String {
        return "$descripcion - $prioridad - $urgente"
    }
}

fun main() {

    val tareas = listOf(
        Tarea("Corregir error crítico en el inicio de sesión", 5),
        Tarea("Entregar el proyecto de clase", 4),
        Tarea("Documentar el código", 2),
        Tarea("Ordenar la carpeta de descargas", 1)
    )
    println(tareas)
    tareas.forEach { if (it.prioridad == 4 || it.prioridad == 5) it.urgente = true }
    println(tareas)
}

