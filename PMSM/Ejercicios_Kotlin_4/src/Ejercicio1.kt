fun celsiusFahrenheit(c: Double): Double = (c * 1.8 + 32)
fun celsiusKelvin(c: Double): Double = (c + 273.15)

fun convertirTemperatura(grados: Double, formula: (Double) -> Double): Double = formula(grados)

fun main() {
    println("Celsius: 9.5")
    println("Fahrenheit: ${convertirTemperatura(9.5, ::celsiusFahrenheit)}")
    println("Kelvin: ${convertirTemperatura(9.5, ::celsiusKelvin)}")
}