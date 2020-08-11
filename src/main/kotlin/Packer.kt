import model.Item
import model.Pack
import model.PackSort

object Packer {

    /**
     * @param maxItemsPerPack - the max items per pack allowed
     * @param maxWeightPerPack - the max weight per pack allowed
     * @param items - the items to pack into packs
     * @param packs - the packs being created
     * @return packs - all the items are returned into packs according to maximums
     */
    fun packItems(maxItemsPerPack: Int,
                  maxWeightPerPack: Double,
                  items: MutableList<Item>,
                  packs: MutableList<Pack> = mutableListOf(Pack(1))): List<Pack> {

        val currentPack = packs.last()

        for (item in items) {
            // Find out what's the available quantity in this pack
            val availableQuantity = maxItemsPerPack - currentPack.quantity()
            // Find out current pack available weight
            val availableWeight = maxWeightPerPack - currentPack.weight()

            if (availableQuantity <= 0 || availableWeight <= 0) {
                // There is no space create new pack
                packs.add(Pack(currentPack.id + 1))
            } else {
                // Find out how many items we can fit in that available weight
                val availableQuantityByWeight = (availableWeight / item.weight).toInt()
                // Find out whether the remaining quantity fits in this pack
                val diff = availableQuantityByWeight - item.quantity

                if (diff > 0) {
                    items.remove(item)
                    if (item.quantity > 0) {
                        currentPack.items.add(item.copy())
                    }
                } else {
                    var maxQuantity = availableQuantity
                    if (availableQuantityByWeight < availableQuantity) {
                        maxQuantity = availableQuantityByWeight
                    }

                    val updatedItem = item.copy()
                    updatedItem.quantity = maxQuantity

                    if (updatedItem.quantity > 0) {
                        currentPack.items.add(updatedItem)

                        item.quantity = item.quantity - maxQuantity
                        if (item.quantity > 0) {
                            packs.add(Pack(currentPack.id + 1))
                        }
                    } else {
                        items.remove(item)
                    }
                }
            }

            return packItems(
                maxItemsPerPack,
                maxWeightPerPack,
                items,
                packs
            )
        }

        return packs
    }

    /**
     * Sorts packs according to PackSort
     *
     * @param sort - the required sort
     * @param packs - the pack array to sort
     */
    fun sortPacks(sort: PackSort, packs: List<Pack>) {
        when(sort) {
            PackSort.LONG_TO_SHORT -> packs.sortedBy { it.length() }
            PackSort.SHORT_TO_LONG -> packs.sortedBy { it.length() }.reversed()
            else -> Unit
        }
    }
}