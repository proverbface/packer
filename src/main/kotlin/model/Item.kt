package model

data class Item(val id: Int, val length: Int, var quantity: Int, val weight: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (length != other.length) return false
        if (quantity != other.quantity) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + length
        result = 31 * result + quantity
        result = 31 * result + weight.hashCode()
        return result
    }


}