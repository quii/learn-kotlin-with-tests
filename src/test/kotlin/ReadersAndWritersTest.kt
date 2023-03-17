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

fun capitaliseAndNewLine(x: String) = "${x.uppercase()}\n"

class ReadEverythingAndThenTransformTest : AllCapsContract({ r, w ->
    r.readLines()
        .joinToString("", transform = ::capitaliseAndNewLine)
        .let { w.write(it) }
})

class AnotherOneWhyNotTest: AllCapsContract({r, w ->
    r.readLines().asSequence().forEach { w.write(capitaliseAndNewLine(it)) }
})

class FoldItTest: AllCapsContract({r, w ->
    r.readLines().fold("") { acc, thing -> acc + capitaliseAndNewLine(thing) }
        .let { w.write(it) }
})

class StreamedTransformsTest : AllCapsContract({ r, w ->
    r.buffered().lines().forEach { w.appendLine(it.uppercase()) }
})
