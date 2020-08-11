
import model.Instruction
import model.Item
import model.Pack
import model.PackSort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class PackerTests {

    @Test
    fun packItemsSingleItem() {
        val instruction = Instruction(
            sort = PackSort.NATURAL,
            maxItemsPerPack = 100,
            maxWeightPerPack = 500.0
        )
        val items = mutableListOf<Item>()
        items.add(Item(id = 1001, length = 6000, quantity = 50, weight = 50.0))

        val packs = Packer.packItems(instruction.maxItemsPerPack,
            instruction.maxWeightPerPack, items)
        assertTrue(packs.size == 5)
    }

    @Test
    fun packItemsValid() {
        val instruction = Instruction(
            sort = PackSort.NATURAL,
            maxItemsPerPack = 40,
            maxWeightPerPack = 500.0
        )

        val items = mutableListOf<Item>()
        items.add(Item(id = 1001, length = 6200, quantity = 30, weight = 9.653))
        items.add(Item(id = 2001, length = 7200, quantity = 50, weight = 11.21))

        val packs = Packer.packItems(instruction.maxItemsPerPack,
            instruction.maxWeightPerPack, items)
        assertTrue(packs.size == 2)

        val pack1 = packs[0]
        val expectedPack1 = Pack(1)
        expectedPack1.items.add(Item(1001, 6200, 30, 9.653))
        expectedPack1.items.add(Item(2001, 7200, 10, 11.21))
        assertEquals(expectedPack1, pack1)
        assertEquals(401.69, pack1.weight())
        assertEquals(7200, pack1.length())

        val pack2 = packs[1]
        val expectedPack2 = Pack(2)
        expectedPack2.items.add(Item(2001, 7200, 40, 11.21))
        assertEquals(expectedPack2, pack2)
        assertEquals(448.4, pack2.weight())
        assertEquals(7200, pack2.length())
    }

    @Test
    fun packItemsSmallWeightPerPack() {
        val instruction = Instruction(
            sort = PackSort.NATURAL,
            maxItemsPerPack = 40,
            maxWeightPerPack = 35.0
        )

        val items = mutableListOf<Item>()
        items.add(Item(id = 1001, length = 6200, quantity = 30, weight = 9.653))
        items.add(Item(id = 2001, length = 7200, quantity = 50, weight = 11.21))

        val packs = Packer.packItems(instruction.maxItemsPerPack,
            instruction.maxWeightPerPack, items)
        assertTrue(packs.size == 27)

        packs.subList(0, 10)
            .forEach { pack ->
                val expectedPack1 = Pack(pack.id)
                expectedPack1.items.add(
                    Item(
                        id = 1001,
                        length = 6200,
                        quantity = 3,
                        weight = 9.653
                    )
                )
                assertEquals(expectedPack1, pack)
                assertEquals(expectedPack1.weight(), pack.weight())
                assertEquals(6200, pack.length())
            }

        packs.subList(11, 26)
            .forEach { pack ->
                val expectedPack2 = Pack(pack.id)
                expectedPack2.items.add(
                    Item(
                        id = 2001,
                        length = 7200,
                        quantity = 3,
                        weight = 11.21
                    )
                )
                assertEquals(expectedPack2, pack)
                assertEquals(expectedPack2.weight(), pack.weight())
                assertEquals(7200, pack.length())
            }

        val pack28 = packs[26]
        val expectedPack28 = Pack(pack28.id)
        expectedPack28.items.add(
            Item(
                id = 2001,
                length = 7200,
                quantity = 2,
                weight = 11.21
            )
        )
        assertEquals(expectedPack28, pack28)
        assertEquals(expectedPack28.weight(), pack28.weight())
    }

    @Test
    fun sortPacksByNatural() {
        val packs = mutableListOf<Pack>()
        packs.add(Pack(1, mutableListOf(Item(1, 100, 1, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 500, 5, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 150, 10, 10.12))))

        //Expect
        val expectedPacks = mutableListOf<Pack>().apply { addAll(packs) }
        Packer.sortPacks(PackSort.NATURAL, packs)
        //Assert
        assertEquals(expectedPacks, packs)
    }

    @Test
    fun sortPacksByShortToLong() {
        val packs = mutableListOf<Pack>()
        packs.add(Pack(1, mutableListOf(Item(1, 100, 1, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 500, 5, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 150, 10, 10.12))))

        //Expect
        val expectedPacks = mutableListOf<Pack>().apply { addAll(packs) }
        expectedPacks.sortedBy { it.length() }.reversed()

        Packer.sortPacks(PackSort.SHORT_TO_LONG, packs)
        //Assert
        assertEquals(expectedPacks, packs)
    }

    @Test
    fun sortPacksByLongToShort() {
        val packs = mutableListOf<Pack>()
        packs.add(Pack(1, mutableListOf(Item(1, 100, 1, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 500, 5, 10.12))))
        packs.add(Pack(1, mutableListOf(Item(1, 150, 10, 10.12))))

        //Expect
        val expectedPacks = mutableListOf<Pack>().apply { addAll(packs) }
        expectedPacks.sortedBy { it.length() }

        Packer.sortPacks(PackSort.LONG_TO_SHORT, packs)
        //Assert
        assertEquals(expectedPacks, packs)
    }
}