import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
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

    data class ShoppingItem(val name: String, val price: Int)

    fun List<ShoppingItem>.totalPrice(): Int =
        this.fold(0) { total, item -> total + item.price }

    class ShoppingBasket(
        val user: String,
        val items: List<ShoppingItem>,
    ): List<ShoppingItem> by items

    @Test
    fun `collections with other properties`() {
        // this approach lets callers leverage the collections API however they like, neat
        val basket = ShoppingBasket("Chris", listOf(
            ShoppingItem("Cheese", 3),
            ShoppingItem("Eggs", 1)),
        )
        expectThat(basket.user).isEqualTo("Chris")
        expectThat(basket.size).isEqualTo(2)
        expectThat(basket.totalPrice()).isEqualTo(4)
    }
}