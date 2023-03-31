import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ScratchTest {
    @Test fun `yeah whatever`() {
        expectThat(1).isEqualTo(1)
    }

    @Test fun `local functions`() {
        expectThat(outer("poop")).isEqualTo("pooppoop")
    }

    @Test fun `anonymous functions vs lambdas, anon funcs can have multiple return paths`() {
        expectThat(op(2) { it * it }).isEqualTo(4)

        expectThat(op(2, fun(x): Int {
            return if(x==0) {
                123
            } else {
                x * x
            }
        })).isEqualTo(4)
    }
}

fun op(x: Int, op: (Int) -> Int): Int {
    return op(x)
}

fun outer(x: String): String {
    fun innerFun(): String {
        return x + "poop"
    }

    return innerFun()
}