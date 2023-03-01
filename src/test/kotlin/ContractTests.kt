import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CalculatorA : Calculator {
    override fun add(a: Int, b: Int): Int {
        return a + b
    }
}

class CalculatorB : Calculator {
    override fun add(a: Int, b: Int): Int {
        return b + a
    }
}

interface Calculator {
    fun add(a: Int, b: Int): Int
}

// contracts should be abstract or junit may try and run them!
abstract class CalculatorContract(private val calculator: Calculator) {
    @Test
    fun `cumulative property`() {
        val a = 1
        val b = 2
        expectThat(calculator.add(a, b)).isEqualTo(calculator.add(b, a))
    }

    @Test
    fun `associative property`() {
        val a = 1
        val b = 2
        val c = 3
        expectThat(calculator.add(a, calculator.add(b, c))).isEqualTo(calculator.add(calculator.add(a, b), c))
    }

    @Test
    fun `identity property`() {
        expectThat(calculator.add(1, 0)).isEqualTo(1)
    }
}

class CalculatorATest : CalculatorContract(CalculatorA())
class CalculatorBTest : CalculatorContract(CalculatorB())