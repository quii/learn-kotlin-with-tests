import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ExtensionMethodsTest {
    @Test
    fun `we can add meow as a method to String`() {
        fun String.meow() = "meow $this"

        assertThat("Chris".meow(), equalTo("meow Chris"))
    }

    @Test
    fun `we can add a static method toURL to String`() {
        fun String.Companion.toURL(link: String) = java.net.URL(link)

        assertThat(String.toURL("https://www.google.com"), equalTo(java.net.URL("https://www.google.com")))
    }

    @Test
    fun `we can inject methods onto functions`() {
        fun <T, R, U> ((T) -> R).andThen(f: (R) -> U): (T) -> U {
            return { t: T -> f(this(t)) }
        }

        fun addTwo(a: Int) = a + 2
        fun addThree(a: Int) = a + 3
        val add5 = ::addTwo.andThen(::addThree)

        assertThat(add5(0), equalTo(5))
    }
}