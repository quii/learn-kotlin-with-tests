import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

sealed class Result<out A, out E> {

}
data class Success<out A>(val value: A) : Result<A, Nothing>()
data class Failure<out E>(val reason: E) : Result<Nothing, E>()

inline fun <A, B, E> Result<A, E>.map(f: (A) -> B): Result<B, E> = when (this) {
    is Success -> Success(f(value))
    is Failure -> this
}

inline fun <A, B, E> Result<A, E>.flatMap(f: (A) -> Result<B, E>): Result<B, E> = when (this) {
    is Success<A> -> f(value)
    is Failure -> this
}

inline fun <A, B, E> Result<A, E>.recover(f: (E) -> A): A = when (this) {
    is Success -> this.value
    is Failure -> f(reason)
}

fun <A> A.asSuccess(): Result<A, Nothing> = Success(this)
fun <E> E.asFailure(): Result<Nothing, E> = Failure(this)
class ResultTest {

    @Test
    fun `success`() {
        expectThat(Success(1).map { it + 1 }).isEqualTo(Success(2))
        expectThat(1.asSuccess().map { it + 1 }).isEqualTo(Success(2))
    }

    @Test
    fun `failure`() {
        val f = Failure("error")
        expectThat(f.map { failure -> f.reason+"poop" }).isEqualTo(Failure("error"))
        expectThat("error".asFailure().map { failure -> f.reason+"poop" }).isEqualTo(Failure("error"))
    }

    @Test
    fun `recover`() {
        val f = Failure("error")
        val recovery = f.recover<String, String, String> { "$it oh no" }
        expectThat(recovery).isEqualTo("error oh no")
    }

}