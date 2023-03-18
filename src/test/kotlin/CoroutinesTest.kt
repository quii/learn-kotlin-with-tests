import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isLessThan
import kotlin.math.exp
import kotlin.system.measureTimeMillis

class CoroutinesTest {
    private val calls = mutableListOf<Int>()

    private suspend fun addThree() {
        delay(100)
        calls += 3
    }

    private suspend fun addTwo() {
        delay(100)
        calls += 2
    }

    @Test
    fun `this is a bad example`() {
        runBlocking {// scope for couroutines
            launch { // launches new coroutine
                delay(100)
                calls +=2
            }
            calls += 1
        }
        expectThat(calls).isEqualTo(mutableListOf(1, 2))
    }

    @Test
    fun `suspending functions`() {
        runBlocking {
            launch { addTwo() }
            calls +=1
        }
        expectThat(calls).isEqualTo(mutableListOf(1, 2))
    }



    @Test
    fun `scopes do not finish until all children have finished`() {
        runBlocking {
            launch { addTwo() }
            calls +=1
            launch { addTwo() }
        }
        expectThat(calls).isEqualTo(mutableListOf(1, 2, 2))
    }

    @Test
    fun `runblocking blocks the current thread, whereas coroutine just suspends`() {
        val elapsedWithCoroutineScope = measureTimeMillis {
            runBlocking {
                coroutineScope {
                    launch { addThree() }
                    launch { addThree() }
                    launch { addThree() }
                    launch { addThree() }
                    launch { addThree() }
                }
            }
        }
        expectThat(elapsedWithCoroutineScope).isLessThan(2000)
    }

    @Test
    fun `jobs`() {
        runBlocking {
            val job1 = launch {addThree()}
            val job2 = launch { addTwo() }
            // join will wait until child completes
            job1.join()
            job2.join()
            addTwo()
        }
        expectThat(calls).isEqualTo(mutableListOf(3, 2, 2))

        runBlocking {
            val job1 = launch { addThree() }
            addTwo() // as we're not waiting for job1, this will happen first
        }

        expectThat(calls).isEqualTo(mutableListOf(3, 2, 2, 2, 3))
    }

}