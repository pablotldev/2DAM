import utils.Colors

class Rectangulo(val ancho: Double, val alto: Double) {
    val area: Double
        get() = if (ancho > 0 && alto > 0) this.ancho * this.alto else 0.0
}

fun main() {
    val rec = Rectangulo(5.5, 2.0)
    println("${Colors.GREEN}Rectángulo area: ${Colors.PURPLE}${rec.area}${Colors.GREEN}, Ancho: ${Colors.PURPLE}${rec.ancho}${Colors.GREEN}, Alto: ${Colors.PURPLE}${rec.alto}${Colors.GREEN}")
}