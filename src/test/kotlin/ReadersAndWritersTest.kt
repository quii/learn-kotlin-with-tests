import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.StringReader
import java.io.StringWriter

abstract class AllCapsContract(val f: (StringReader, StringWriter) -> Unit) {
    @Test
    fun `whatever man`() {
        val r = StringReader("""lol
            |wtf
            |lmao
        """.trimMargin())

        val w = StringWriter()

        f(r, w)

        val got = w.toString()

        expectThat(got).isEqualTo("""LOL
            |WTF
            |LMAO
        """.trimMargin())
    }
}

fun notEfficientAllCapper(r: StringReader, w: StringWriter) {
    val allCaps = r.readLines().map(String::uppercase).joinToString("\n")
    w.write(allCaps)
}

class NotEfficientAllCapsTest : AllCapsContract(::notEfficientAllCapper)