package ejercicio1

import utils.Colors

class SensorAcelerometro(id: String, override var frecuenciaHz: Int) : Sensor(id, frecuenciaHz) {
   constructor(id: String): this(id, 50)
   fun leerDatos(): String {
      if (activo) return "Activo"
      return "Inactivo"
   }

}

fun main() {
   // 1. Prueba del constructor secundario (frecuenciaHz por defecto a 50)
   val acelerometro1 = SensorAcelerometro("ACEL-01")

   // 2. Prueba del constructor primario (frecuenciaHz asignada manualmente)
   val acelerometro2 = SensorAcelerometro("ACEL-02", 120)

   // 3. Clon para probar la función equals (mismo ID, distinta frecuencia)
   val acelerometroClon = SensorAcelerometro("ACEL-01", 80)

   println("${Colors.BLUE}--- ESTADO INICIAL ---")
   println(Colors.PURPLE + acelerometro1.toString())
   println(acelerometro2.toString())

   println("${Colors.BLUE}\n--- LECTURA DE DATOS ---")
   println("${Colors.GREEN}Lectura ACEL-01 (Inactivo): ${Colors.PURPLE + acelerometro1.leerDatos()}")

   acelerometro1.activo = true
   println("${Colors.GREEN}Lectura ACEL-01 (Activo): ${Colors.PURPLE + acelerometro1.leerDatos()}")
   println("${Colors.GREEN}Nuevo estado: ${Colors.PURPLE + acelerometro1}")

   println("${Colors.BLUE}\n--- PRUEBA DE IGUALDAD (equals) ---")
   // Llama implícitamente al método equals que has sobreescrito
   println("${Colors.GREEN}¿acelerometro1 es igual a acelerometro2?:${Colors.PURPLE} ${acelerometro1 == acelerometro2}") // false
   println("${Colors.GREEN}¿acelerometro1 es igual a acelerometroClon?: ${Colors.PURPLE}${acelerometro1 == acelerometroClon}") // true
}