import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class NullTest {
    @Test
    fun `dealing with null`(){
        fun foo(s: String?): Int? {
            return s?.length
        }

        assertThat(foo("abc"), equalTo(3))
        assertThat(foo(null), equalTo(null))
    }

    @Test
    fun `the elvis operator`() {
        fun foo(s: String?): Int {
            return s?.length ?: 0
        }

        assertThat(foo("abc"), equalTo(3))
        assertThat(foo(null), equalTo(0))
    }

    @Test
    fun `using when and null`() {
        fun foo(s: String?): Int {
            return when (s) {
                null -> 0 // if you commennt out this line it wont compile
                else -> s.length
            }
        }

        assertThat(foo("abc"), equalTo(3))
        assertThat(foo(null), equalTo(0))
    }

    @Test
    fun `combining with let is quite common`() {
        val score: Int? = null
        val bonus = 2
        val finalScore = score?.let { it*bonus } ?: 0

        expectThat(finalScore).isEqualTo(0)
    }

    abstract class BonusMultiplierContract(private val f: (Int) ->Int) {
        @Test
        fun `applies bonus multiplier if score is gt 10`() {
            expectThat(f(10)).isEqualTo(10)
            expectThat(f(11)).isEqualTo(22)
        }
    }

    class TakeIfBonusMultiplier : BonusMultiplierContract({ score -> score.takeIf { it > 10 }?.let { it * 2 } ?: score})
    class WhenBonusMultiplier : BonusMultiplierContract({score -> when {
        score > 10 -> score * 2
        else -> score
    } })
}