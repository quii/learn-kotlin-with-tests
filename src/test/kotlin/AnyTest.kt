import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.throws
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class AnyTest {
    @Test
    fun `any`() {
        val a: Any = 1
        val b: Any = 2

        assertThat(a to b, equalTo(Pair(1, 2)))
    }
}