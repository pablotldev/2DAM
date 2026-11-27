import kotlin.math.ceil

fun main() {

    val preciosSinIva = listOf(10.0, 25.5, 100.0, 42.0)
    val preciosConIva = preciosSinIva.map { ceil(it * 1.21 * 100) / 100 }

    println(preciosSinIva)
    println(preciosConIva)
}

