import model.Instruction
import model.Item

fun main() {
    // Read instruction
    val instruction: Instruction? = ConsoleProcessor.readInstruction()

    if (instruction == null) {
        return
    }

    // Read items
    val items: MutableList<Item> = ConsoleProcessor.readItems()

    // Create packs
    val packs = Packer.packItems(
        instruction.maxItemsPerPack,
        instruction.maxWeightPerPack,
        items
    )

    // Sort packs
    Packer.sortPacks(instruction.sort, packs)

    // Print out
    packs.forEach { pack ->
        print("model.Pack Number: ${pack.id}\n")
        pack.items.forEach { item ->
            print("${item.id},${item.length},${item.quantity},${item.weight}\n")
        }
        print("model.Pack Length: ${pack.length()}, model.Pack Weight: ${pack.weight()}\n\n")
    }
}