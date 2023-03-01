import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.concurrent.atomic.AtomicReference

class AtomicValue(thing: String) {
    val value = AtomicReference(thing)

    fun get() = value.get()
    fun set(thing: String) = value.set(thing)
}

class AtomicTest {
    @Test
    fun `atomic`() {
        val x = AtomicValue("Hello")
        expectThat(x.get()).isEqualTo("Hello")
        x.set("World")
        expectThat(x.get()).isEqualTo("World")
    }
}