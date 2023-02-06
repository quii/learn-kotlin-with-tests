import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class DestructuringTest {
    @Test
    fun `destructuring`() {
        val (a, b) = Pair(1, 2)
        assertThat(a, equalTo(1))
        assertThat(b, equalTo(2))
    }

    @Test
    fun `destructuring triples`() {
        val (a, b, c) = Triple(1, 2, 3)
        assertThat(a, equalTo(1))
        assertThat(b, equalTo(2))
        assertThat(c, equalTo(3))
    }

    @Test
    fun `destructuring lists`() {
        val (a, b, c) = listOf(1, 2, 3)
        assertThat(a, equalTo(1))
        assertThat(b, equalTo(2))
        assertThat(c, equalTo(3))
    }

    @Test
    fun `destructuring data classes`() {
        data class DataClass(val a: Int, val b: Int)
        val (a, b) = DataClass(1, 2)
        assertThat(a, equalTo(1))
        assertThat(b, equalTo(2))
    }
}