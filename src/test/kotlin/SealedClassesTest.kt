import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class SealedClassesTest {
    sealed class Colour {
        object Red : Colour()
        object Green : Colour()
        object Blue : Colour()
    }

    @Test
    fun `sealed classes`() {
        fun foo(c: Colour): String {
            return when (c) {
                is Colour.Red -> "red"
                is Colour.Green -> "green"
                is Colour.Blue -> "blue"
            }
        }

        assertThat(foo(Colour.Red), equalTo("red"))
        assertThat(foo(Colour.Green), equalTo("green"))
        assertThat(foo(Colour.Blue), equalTo("blue"))
    }
}