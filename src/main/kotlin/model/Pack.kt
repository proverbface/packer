package model

import kotlin.math.*

class Pack(val id: Int,
           val items: MutableList<Item> = mutableListOf()) {

    fun length(): Int = items.maxBy { it.length }?.length ?: 0

    fun quantity(): Int =
        items.map { it.quantity }.stream().reduce(0) { a, b -> a + b }

    fun weight(): Double =
        items.map { it.weight * it.quantity }
            .stream()
            .reduce(0.0) { a, b -> a + b }.nextDown()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pack

        if (id != other.id) return false
        if (items != other.items) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + items.hashCode()
        return result
    }

    override fun toString(): String {
        return "model.Pack(id=$id, items=$items)"
    }
}