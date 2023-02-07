import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

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
}