fun main() {

    val cuadrado: (Int) -> Int = { lado: Int -> lado * lado }
    val esPositivo: (Int) -> Boolean = { lado: Int -> lado > 0 }
    val saludar: (String, Int) -> String = { nombre: String, hora: Int -> if (hora < 12) "Buenos días $nombre" else "Buenas tardes $nombre" }

    println("Cuadrado de 5: ${cuadrado(5)}")
    println("Es positivo (-5)? ${esPositivo(-5)}")
    println("Saludo (Nombre: Pablo, Hora: 13): ${saludar("Pablo", 13)}")

}