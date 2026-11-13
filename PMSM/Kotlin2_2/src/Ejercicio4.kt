class ContadorPasos() {
    var pasos: Int = 0
        private set
    fun darPaso() {
        pasos++
    }
    fun reiniciar() {
        pasos = 0
    }
}