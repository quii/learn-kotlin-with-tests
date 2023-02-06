import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ExpressionsKtTest {
    @Test
    fun `if statements can be expressions`() {
        val result = if (true) 1 else 2
        assertThat(result, equalTo(1))
    }

    @Test
    fun `exception as expression 1`() {
        val result = exceptionsAsExpressions(false)
        assertThat(result, equalTo(1))
    }

    @Test
    fun `exception as expression 2`() {
        val result = exceptionsAsExpressions(true)
        assertThat(result, equalTo(2))
    }

}

fun exceptionsAsExpressions(blowup: Boolean): Int {
    return try {
        if (blowup) {
            throw Exception("Boom!")
        }
        1
    } catch (e: Exception) {
        2
    }
}