import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class SumKtTest {

    @Test
    fun `sum a list with a for loop`() {
        assertThat(sum(listOf(1, 2)), equalTo(3))
    }

    @Test
    fun `sum a list with fold`() {
        assertThat(sum2(listOf(1, 2)), equalTo(3))
    }

    @Test
    fun `sum a list with varargs`() {
        assertThat(sum3(1, 2), equalTo(3))
    }
}