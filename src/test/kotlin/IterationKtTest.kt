import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class IterationKtTest {
    @Test
    fun `playing around with lists`() {
        assertThat( List(5) {"a"}, equalTo(listOf("a", "a", "a", "a", "a")))
    }

    @Test
    fun `it repeats characters`() {
        assertThat(repeatMe('a'), equalTo("aa"))
        assertThat(repeatMe('a', 3), equalTo("aaa"))
    }
}