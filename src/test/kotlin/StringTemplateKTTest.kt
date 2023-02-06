import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class StringTemplateKTTest {
    @Test
    fun `string templating`() {
        val version = 1.3
        val info = "Kotlin $version"
        assertThat(info, equalTo("Kotlin 1.3"))
    }

    @Test
    fun `multiline string`() {
        val info = """
            Kotlin
            is
            awesome
        """.trimIndent()
        assertThat(info, equalTo("Kotlin\nis\nawesome"))
    }
}