fun esLongitudValida(pass: String): Boolean = (pass.length > 8)
fun tieneNumero(pass: String): Boolean = (pass.any { it.isDigit() })

fun validarPassword(pass: String, regla: (String) -> Boolean): Boolean = regla(pass)

fun main() {
    val pass: String = "Admin.1234"
    println("Contraseña: $pass")
    println("Longitud válida? ${validarPassword(pass, ::esLongitudValida)}")
    println("Tiene número? ${validarPassword(pass, ::tieneNumero)}")
}