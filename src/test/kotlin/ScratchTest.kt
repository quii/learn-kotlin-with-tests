import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.exp

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

    @Test fun `local returns`() {
        // with the below, the return doesn't return from the lambda, it returns the function
        fun nonLocalReturn(): Int {
            (1..100).forEach {
                if (it==10) {
                    return 10
                }
            }
            return 5
        }
        expectThat(nonLocalReturn()).isEqualTo(10)

        //local returns let us do, well what do you think...
        fun localReturn(): Int {
            (1..100).forEach {
                if (it==10) {
                    return@forEach
                }
            }
            return 5
        }
        expectThat(localReturn()).isEqualTo(5)

        // can also label
        fun labelReturn(): Int {
            (1..100).forEach butts@{
                if (it==10) {
                    return@butts
                }
            }
            return 5
        }
        expectThat(labelReturn()).isEqualTo(5)

        // remember this only applies to lambdas, functions and anon functions behave more how you'd expect (i suppose)
        fun returnWithAnon(): Int {
            (1..100).forEach(fun(el) {
                if(el==10) {
                    return
                }
            })
            return 5
        }
        expectThat(returnWithAnon()).isEqualTo(5)
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