import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

// Identity<T> is a type builder, not a type
data class Identity<T>(private val value:T) {
    fun <R> map(f: (T) -> R): Identity<R> = Identity(f(value))

    companion object {
        fun <T, R> lift(f: (T) -> R): (Identity<T>) -> Identity<R> = { it.map(f) }
    }
}

infix fun <A, B, C>((A) -> B).andThen(g: (B) -> C): (A) -> C = { a: A -> g(this(a)) }
class FunctorsTest {
    @Test
    fun `compose functors`() {
        val f: (Int) -> Int = { it + 1 }
        val g: (String) -> Int = { it.length }
        val h: (String) -> String = { "$it!" }
        val composed = h andThen g andThen f
        val result = composed("Hello")
        expectThat(result).isEqualTo(7)
    }

    @Test
    fun `identity example`() {
        val a: Identity<String> = Identity("Hello")
        val b1: Identity<Int> = a.map(String::length)

        val strLenLifted = Identity.lift(String::length)
        val b2: Identity<Int> = strLenLifted(a)

        expectThat(b1).isEqualTo(Identity(5))
        expectThat(b2).isEqualTo(Identity(5))
    }
}