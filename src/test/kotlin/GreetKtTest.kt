import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class GreetKtTest {

    @Test
    fun `greet people with a friendly meeting, with different languages`() {
        assertThat(greet(), equalTo("Hello, World!"))
        assertThat(greet(name = "Chris"), equalTo("Hello, Chris!"))
        assertThat(greet(lang=Language.FRENCH), equalTo("Bonjour, World!"))
    }
}