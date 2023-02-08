import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class DataClassTest {
    data class Person(val name: String, val age: Int)

    @Test
    fun `data classes`() {
        val chris = Person("Chris", 30)
        val chris2 = Person("Chris", 30)
        val chris3 = chris.copy()
        val chris4 = chris.copy(age = 31)

        assertThat(chris, equalTo(chris2))
        assertThat(chris, equalTo(chris3))
        assertThat(chris, !equalTo(chris4))
        assertThat(chris.toString(), equalTo("Person(name=Chris, age=30)"))
    }

    @Test
    fun `destructuring`() {
        val (name, age) = Person("Chris", 30)
        assertThat(name, equalTo("Chris"))
        assertThat(age, equalTo(30))
    }
}