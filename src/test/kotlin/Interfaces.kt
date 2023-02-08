import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class Interfaces {
    interface Adder {
        fun add(x: Int): Int

        companion object {
            fun combine(a: Adder, b: Adder): Adder {
                return object : Adder {
                    override fun add(x: Int): Int {
                        return a.add(b.add(x))
                    }
                }
            }
        }
    }

    @Test
    fun `interfaces can have companion objects`() {
        val add2 = object : Adder {
            override fun add(x: Int): Int {
                return x + 2
            }
        }

        val add3 = object : Adder {
            override fun add(x: Int): Int {
                return x + 3
            }
        }

        val add5 = Adder.combine(add2, add3)
        assertThat(add5.add(0), equalTo(5))
    }
}