import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class OverloadingTest {
    data class Bitcoin(val amount: Int) {
        operator fun plus(b: Bitcoin): Bitcoin {
            return Bitcoin(this.amount + b.amount)
        }
    }

    @Test
    fun `you can add bitcoins`() {
        val a = Bitcoin(2)
        val b = Bitcoin(3)
        assertThat(a + b, equalTo(Bitcoin(5)))
    }
}