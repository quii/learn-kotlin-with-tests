import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ChainingKTTest {
    @Test
    fun `can chain operations with let`() {
        val result = 10
            .let(::add2)
            .let(::add3)
            .let(::add4)
        assertThat(result, equalTo(19))
    }
}

fun add2(a: Int) = a + 2
fun add3(a: Int) = a + 3

fun add4(a: Int) = a + 4