import utils.Colors

class Persona(val nombre: String, var edad: Int) {
    fun cumplirAnios() {
        this.edad++
        println("${Colors.GREEN}Felicidades ${Colors.PURPLE}$nombre${Colors.GREEN}, ya tienes${Colors.PURPLE} $edad ${Colors.GREEN}años!!")
    }
}

fun main() {
    Persona("Paco", 18).cumplirAnios()
}