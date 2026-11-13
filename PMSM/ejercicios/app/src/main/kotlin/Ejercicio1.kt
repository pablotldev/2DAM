import kotlin.system.exitProcess

fun main() {

    var media = 0.0
    val div: Int = 4
    println("Introduce 4 notas: ")
    for (i in 1..4) {
        print("Introduce la nota $i: ")
        media += readlnOrNull()?.toDoubleOrNull() ?: 0.0
    }
    media /= div
    println("Media: $media")
    if (media in 0.0..4.99) {
        println("Aprobado")
        exitProcess(0)
    } else if (media in 5.0..10.0) {
        println("Suspenso")
        exitProcess(0)
    }
    print("Media no válida")
}

