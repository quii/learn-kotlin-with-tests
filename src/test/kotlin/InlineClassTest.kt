import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

inline class Name(val value: String)
inline class Age(val value: Int)
class InlineClassTest {
    @Test
    fun `tiny types with inline classes, wont get boxed`() {


        val name = Name("Chris")
        val age = Age(30)

        assertThat(name.value, equalTo("Chris"))
        assertThat(age.value, equalTo(30))
    }
}