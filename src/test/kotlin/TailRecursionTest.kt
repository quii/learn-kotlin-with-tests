import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun interface Factorial {
    operator fun invoke(n: Int): Int
}

abstract class FactorialContract(val f: Factorial) {
    @Test fun `death to stackoverflow`() {
        expectThat(f(5)).isEqualTo(120)
    }
}

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
// just like Go, the language of elite programmers.
tailrec fun actuallyTRFactorial(number: Int, accumulator: Int = 1): Int {
    return when(number) {
        0 -> accumulator
        else -> actuallyTRFactorial(number -1, accumulator * number)
    }
}

class NonTailRecursiveFactorialTest : FactorialContract(::factorial)

class TailRecursiveFactorialTest: FactorialContract(::factorialTR)

class ActuallyTailRecursiveFactorialTest: FactorialContract(::actuallyTRFactorial)