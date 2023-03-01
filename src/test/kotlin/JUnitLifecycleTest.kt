import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class JUnitLifecycleTest {
    private var counter = 0

    @BeforeEach
    fun `dont use before each in general, just for debug`() {
        println("counter is set at $counter")
    }

    @Test
    fun `increment counter`() {
        counter++
        expectThat(counter).isEqualTo(1)
    }

    @Test fun `counter is 0 at start of test even though the other test mutated it, because a new instance is made per test`() {
        expectThat(counter).isEqualTo(0)
    }
}