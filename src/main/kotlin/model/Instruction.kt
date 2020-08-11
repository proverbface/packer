package model

data class Instruction(val sort: PackSort,
                       val maxItemsPerPack: Int,
                       val maxWeightPerPack: Double) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Instruction

        if (sort != other.sort) return false
        if (maxItemsPerPack != other.maxItemsPerPack) return false
        if (maxWeightPerPack != other.maxWeightPerPack) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sort.hashCode()
        result = 31 * result + maxItemsPerPack
        result = 31 * result + maxWeightPerPack.hashCode()
        return result
    }

    override fun toString(): String {
        return "Instruction(sort=$sort, maxItemsPerPack=$maxItemsPerPack, maxWeightPerPack=$maxWeightPerPack)"
    }
}