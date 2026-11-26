fun main() {
    val precio: Double = 99.99
    println("Precio: $$precio")
    println("Descuento Porcentaje (15%): ${calcularPrecioFinal(precio, 15.0, ::descuentoPorcentaje)}")
    println("Descuento fijo (15): ${calcularPrecioFinal(precio, 15.0, ::descuentoFijo)}")
}

fun calcularPrecioFinal(precio: Double, valor: Double, politica: (Double, Double) -> Double): Double = politica(precio, valor)
fun descuentoPorcentaje(precio: Double, descuento: Double): Double = (precio - precio * (descuento / 100))
fun descuentoFijo(precio: Double, descuento: Double): Double = (if (precio - descuento >= 0.0) precio - descuento else 0.0)

