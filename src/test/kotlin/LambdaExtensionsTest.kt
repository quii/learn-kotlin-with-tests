import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

data class Request(val path: String, var method: String, var body: String)

infix fun String.to(block: Request.() -> Unit): Request {
    val req = Request(this, "GET", "")
    req.block()
    return req
}

class LambdaExtensionsTest {
    @Test fun `type-safe builders`() {
        val req = "/greet" to {
            method = "POST"
            body = "poop"
        }
        expectThat(req).isEqualTo(Request("/greet", "POST", "poop"))
    }
}