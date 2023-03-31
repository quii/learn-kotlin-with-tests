import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun factorial(number: Int): Int = when(number) {
    0, 1 -> 1
    else -> number * factorial(number -1)
}

fun factorialTR(number: Int, accumulator: Int = 1): Int {
    return when(number) {
        0 -> accumulator
        else -> factorialTR(number -1, accumulator * number)
    }
}

// look in the bytecode for a GOTO :) it basically replaces the recursive calls with a for loop
tailrec fun actuallyTRFactorial(number: Int, accumulator: Int = 1): Int {
    return when(number) {
        0 -> accumulator
        else -> actuallyTRFactorial(number -1, accumulator * number)
    }
}

class FactorialTests {
    fun interface Factorial {
        operator fun invoke(n: Int): Int
    }

    abstract class FactorialContract(val f: Factorial) {
        @Test fun `it can calculate the factorial of 5, woah`() {
            expectThat(f(5)).isEqualTo(120)
        }
    }
    @Nested
    inner class NonTailRecursiveFactorialTest : FactorialContract(::factorial)

    @Nested
    inner class TailRecursiveFactorialTest: FactorialContract(::factorialTR)

    @Nested
    inner class ActuallyTailRecursiveFactorialTest: FactorialContract(::actuallyTRFactorial)
}


