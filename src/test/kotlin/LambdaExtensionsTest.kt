import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

data class Request(val path: String, var method: String, var body: String) {
    infix fun method(method: () -> String) {
        this.method = method()
    }
    infix fun body(body: () -> String) {
        this.body = body()
    }
}

infix fun String.to(block: Request.() -> Unit): Request {
    val req = Request(this, "GET", "")
    req.block()
    return req
}

class LambdaExtensionsTest {
    //looks nice i suppose, but relies on mutability. Wonder how HTTP4k does it
    @Test fun `type-safe builders`() {
        val req = "/greet" to {
            method {
                "POST"
            }
            body {
                "poop"
            }
        }
        expectThat(req).isEqualTo(Request("/greet", "POST", "poop"))
    }
}