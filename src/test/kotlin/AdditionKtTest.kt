import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

fun randomNaturalNumber() = (0..100_000_000).random()

class AdditionKtTest {
    @Test
    fun `zero identity`() {
        repeat(100) {
            val n = randomNaturalNumber()
            assertThat(n + 0, equalTo(n))
        }
    }

    @Test
    fun `commutative property`() {
        repeat(100) {
            val n = randomNaturalNumber()
            val m = randomNaturalNumber()
            assertThat(n + m, equalTo(m + n))
        }
    }

    @Test
    fun `associative property`() {
        repeat(100) {
            val n = randomNaturalNumber()
            val m = randomNaturalNumber()
            val k = randomNaturalNumber()
            assertThat(n + (m + k), equalTo((n + m) + k))
        }
    }
}