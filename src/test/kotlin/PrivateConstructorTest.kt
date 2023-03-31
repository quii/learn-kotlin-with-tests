import org.junit.jupiter.api.Test

class PrivateConstructorTest {
    class Log private constructor(val fileName: String) {
        companion object Factory {
            fun createLogger(fileName: String) = Log(fileName)
        }
    }

    @Test
    fun `if you wish to hide the construtor to others you can mark it private`() {
        Log.createLogger("blah.txt")
        // Log("poop") <- this wont compile
    }
}