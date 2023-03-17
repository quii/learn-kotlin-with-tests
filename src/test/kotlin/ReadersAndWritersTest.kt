import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.Reader
import java.io.StringReader
import java.io.StringWriter
import java.io.Writer

abstract class AllCapsContract(val f: (Reader, Writer) -> Unit) {
    @Test
    fun `whatever man`() {
        val r = StringReader(
            """lol
wtf
lmao"""
        )

        val w = StringWriter()

        f(r, w)

        val got = w.toString()

        expectThat(got).isEqualTo(
            """LOL
WTF
LMAO
"""
        )
    }
}

class ReadEverythingAndThenTransformTest : AllCapsContract({ r, w ->
    r.readLines()
        .map(String::uppercase)
        .joinToString("") { it + "\n" }
        .let { w.write(it) }
})

class StreamedTransformsTest : AllCapsContract({ r, w ->
    r.buffered().lines().forEach { if (it.isNotBlank()) w.appendLine(it.uppercase()) }
})
