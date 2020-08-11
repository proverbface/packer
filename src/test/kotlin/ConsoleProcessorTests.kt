import model.Instruction
import model.Item
import model.PackSort
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ConsoleProcessorTests {

    @Test
    fun validNaturalInstruction() {
        val instruction = ConsoleProcessor.parseInstruction("NATURAL, 10, 50")
        assertEquals(Instruction(PackSort.NATURAL, 10, 50.0),instruction)
    }

    @Test
    fun validLongInstruction() {
        val instruction = ConsoleProcessor.parseInstruction("LONG_TO_SHORT, 1000, 500.12")
        assertEquals(Instruction(PackSort.LONG_TO_SHORT, 1000, 500.12),instruction)
    }

    @Test
    fun validShortInstruction() {
        val instruction = ConsoleProcessor.parseInstruction("SHORT_TO_LONG, 1000, 500.12")
        assertEquals(Instruction(PackSort.SHORT_TO_LONG, 1000, 500.12),instruction)
    }

    @Test
    fun invalidInstruction() {
        var instruction = ConsoleProcessor.parseInstruction("SHORT_TsdgO_LONG, 1000,500.12")
        assertEquals(null, instruction)
        instruction = ConsoleProcessor.parseInstruction("SHORT_TO_LONG,sdsd,500.12")
        assertEquals(null, instruction)
        instruction = ConsoleProcessor.parseInstruction("SHORT_TO_LONG, 100, sdgsdg")
        assertEquals(null, instruction)
        instruction = ConsoleProcessor.parseInstruction(null)
        assertEquals(null, instruction)
        instruction = ConsoleProcessor.parseInstruction("")
        assertEquals(null, instruction)
        instruction = ConsoleProcessor.parseInstruction(",,,,")
        assertEquals(null, instruction)
    }

    @Test
    fun validItem() {
        val item = ConsoleProcessor.parseItem("1001, 6200, 30, 9.653")
        assertEquals(Item(1001, 6200, 30, 9.653),item)
    }

    @Test
    fun invalidItem() {
        var item = ConsoleProcessor.parseItem("sdfsdf, 6200, 30, 9.653")
        assertEquals(null, item)
        item = ConsoleProcessor.parseItem("1001, dfgdfg, 30, 9.653")
        assertEquals(null, item)
        item = ConsoleProcessor.parseItem("1001, 6200, sdfgsd, 9.653")
        assertEquals(null, item)
        item = ConsoleProcessor.parseItem("1001, 6200, 30, sdfsdf")
        assertEquals(null, item)
        item = ConsoleProcessor.parseItem("")
        assertEquals(null, item)
        item = ConsoleProcessor.parseItem(null)
        assertEquals(null, item)
    }

    @Test
    fun shouldStopRead() {
        val stopRead = ConsoleProcessor.shouldStopRead(" ")
        assertTrue(stopRead)
    }

    @Test
    fun shouldNotStopRead() {
        val stopRead = ConsoleProcessor.shouldStopRead("1001 ")
        assertTrue(!stopRead)
    }
}