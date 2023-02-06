import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WhenTest {
    @Test
    fun `basic when`() {
        val n = 10
        val result = when (n) {
            0 -> "zero"
            1 -> "one"
            2 -> "two"
            else -> "many"
        }
        assertThat(result, equalTo("many"))
    }

    @Test
    fun `predicates in when`() {
        val n = 10
        val result = when {
            n < 0 -> "negative"
            n == 0 -> "zero"
            n > 0 -> "positive"
            else -> "unknown"
        }
        assertThat(result, equalTo("positive"))
    }
    @Nested
    inner class ListFun {
        fun championPick(champion: String): String = when(champion) {
            "Kassadin" -> "Swain"
            in listOf("Yasuo", "Master Yi") -> "Lissandra"
            else -> "Vex"
        }

        @Test
        fun `pick a counter to kassadin`() {
            assertThat(championPick("Kassadin"), equalTo("Swain"))
        }

        @Test
        fun `pick a counter to dashy bastards`() {
            assertThat(championPick("Yasuo"), equalTo("Lissandra"))
        }
    }
}