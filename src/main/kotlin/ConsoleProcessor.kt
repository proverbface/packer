import model.Instruction
import model.Item
import model.PackSort

object ConsoleProcessor {

    fun shouldStopRead(input: String?): Boolean = input == " "

    /**
     * Reads instruction until found or stop read found
     */
    fun readInstruction(): Instruction? {
        while (true) {
            val instructionLine = readLine()

            if (shouldStopRead(instructionLine)) {
                break
            }

            val instruction = parseInstruction(instructionLine)
            if (instruction != null) {
                return instruction
            }
        }
        return null
    }

    /**
     * Reads items from console until stop read is found
     */
    fun readItems(): MutableList<Item> {
        val items = mutableListOf<Item>()

        while (true) {
            val itemLine = readLine()

            if (shouldStopRead(itemLine)) {
                break
            }

            val item = parseItem(itemLine)
            item?.let {
                items.add(item)
            }
        }

        return items
    }

    /**
     * Parses instructions
     *
     * @param instructionLine the line to parse
     * @return instruction or null if not available
     */
    fun parseInstruction(instructionLine: String?): Instruction? {
        var instruction: Instruction? = null

        if (instructionLine.isNullOrEmpty()) {
            return instruction
        }

        try {
            val (order, maxItems, maxWeight) = instructionLine.split(',')
            instruction = Instruction(
                PackSort.valueOf(order.trim()),
                maxItems.trim().toInt(),
                maxWeight.trim().toDouble()
            )
        } catch (e: Exception) {
            print("This is an invalid instruction." +
                    " Sorting order can be one of ${PackSort.values().joinToString()}," +
                    " max items per pack and max weight per pack must be an integer")
            print("Please enter your instruction again.")
        }

        return instruction
    }

    /**
     * Parses items
     *
     * @param itemLine the line to parse
     * @return items or null if not available
     */
    fun parseItem(itemLine: String?): Item? {
        var item: Item? = null

        if (itemLine.isNullOrEmpty()) {
            return item
        }

        try {
            val (id, length, quantity, weight) = itemLine.split(',')

            item = Item(
                id.trim().toInt(),
                length.trim().toInt(),
                quantity.trim().toInt(),
                weight.trim().toDouble()
            )
        } catch (e: Exception) {
            print("This is an invalid item." +
                    " id, length and quantity must be integers while weight must be a double: line $itemLine")
        }

        return item
    }

}