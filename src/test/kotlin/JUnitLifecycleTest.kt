import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

//summarising https://www.youtube.com/watch?v=cs2Wu9Co-2s

private val someConstantThing = "you might consider having this stuff living here, rather than in the test class because it can be shared for each test, all instances of the class will be GC'd after each test"
// importantly, putting fields in the test communicate state

// note these tests are ran in parallel with "mutable state" but it doesn't matter because the tests are ran with newinstances of the class
@Execution(ExecutionMode.CONCURRENT)
class JUnitLifecycleTest {
    private var counter = 0

    @BeforeEach
    fun `no need to use before each in general, just for debug`() {
        println("counter is set at $counter")
    }

    @Test fun `increment counter`() {
        counter++
        expectThat(counter).isEqualTo(1)
    }

    @Test fun `counter is 0 at start of test even though the other test mutated it, because a new instance is made per test`() {
        expectThat(counter).isEqualTo(0)
    }

    @Test fun `another for fun`() {
        counter = 32908423
        expectThat(counter).isEqualTo(32908423)
        expectThat(someConstantThing).isNotEmpty()
    }
}