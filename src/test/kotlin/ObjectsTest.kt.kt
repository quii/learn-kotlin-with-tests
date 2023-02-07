import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ObjectsTest {
    @Test
    fun `you can just create objects`() {
        val a = object {
            val x = 1
            val y = 2
        }
        assertThat(a.x, equalTo(1))
        assertThat(a.y, equalTo(2))
    }

    interface Animal {
        fun speak(): String
    }
    @Test
    fun `you cant use anon objects as return types or arguments, but they can impl interfaces`() {
        fun speak(animal: Animal): String {
            return "this animal goes ${animal.speak()}"
        }
        val dog = object : Animal {
            override fun speak(): String {
                return "woof"
            }
        }
        val cat = object : Animal {
            override fun speak(): String {
                return "meow"
            }
        }
        assertThat(speak(dog), equalTo("this animal goes woof"))
        assertThat(speak(cat), equalTo("this animal goes meow"))
    }

    object Computer {
        val numberOfProcessors = run {
            val availableProcessors = Runtime.getRuntime().availableProcessors()
            println("this computer has $availableProcessors processors")
            availableProcessors
        }
    }
    @Test
    fun `singletons`() {
        assertThat(Computer.numberOfProcessors, equalTo(Runtime.getRuntime().availableProcessors()))
    }
}