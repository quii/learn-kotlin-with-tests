import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates.observable

interface Worker {
    fun work(): String
}

class JavaProgrammer : Worker {
    override fun work(): String {
        return "I'm a Java programmer, so I'm working on a Java project"
    }
}

class GoProgrammer : Worker {
    override fun work() : String {
        return "I'm a Go programmer, so I'm working on a Go project"
    }
}

class DIManager(val worker: Worker) {
    fun work(): String {
        return worker.work()
    }
}

class Manager(val worker: Worker): Worker by worker

class DecoratorManager(val worker: Worker): Worker by worker {
    override fun work(): String {
        return "WORKING: " + worker.work()
    }
}

class DelegationTest {
    @Test
    fun `delegation via di`() {
        val diManager = DIManager(JavaProgrammer())
        assertThat(diManager.work(), equalTo("I'm a Java programmer, so I'm working on a Java project"))
    }

    @Test
    fun `delegation with by, note the difference is we dont have to add a work method, it's like embedding in Go`() {
        val manager = Manager(JavaProgrammer())
        assertThat(manager.work(), equalTo("I'm a Java programmer, so I'm working on a Java project"))
    }

    @Test
    fun `decoration and deletion`() {
        val manager = DecoratorManager(JavaProgrammer())
        assertThat(manager.work(), equalTo("WORKING: I'm a Java programmer, so I'm working on a Java project"))
    }


    @Test
    fun `built in observable delegate`() {
        var edits = 0
        var name: String by observable("initial value") { property, oldValue, newValue ->
            edits++
        }
        assertThat(edits, equalTo(0))
        name = "new value"
        assertThat(edits, equalTo(1))
    }

    @Test
    fun `built in lazy delegate`() {
        var calls = 0
        fun expensiveComputation(): String {
            calls++
            return "expensive computation"
        }
        assertThat(calls, equalTo(0))

        val lazyValue: String by lazy { expensiveComputation() }
        assertThat(calls, equalTo(0))
        assertThat(lazyValue, equalTo("expensive computation"))
        assertThat(calls, equalTo(1))
    }
}