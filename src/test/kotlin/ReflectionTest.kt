import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class ReflectionTest {

    @Test fun `we can check an object is a type with is`() {
        val isInt: (Any) -> Boolean = {it is Int}
        expectThat(isInt(1)).isTrue()
        expectThat(isInt("poo")).isFalse()
    }
}