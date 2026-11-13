package ejercicio1

open class Sensor(val id: String, open val frecuenciaHz: Int) {
    var activo: Boolean = false
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sensor) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

    override fun toString(): String {
        return "[Sensor ID: $id] Frec: ${frecuenciaHz}Hz | Activo: $activo]"
    }
}