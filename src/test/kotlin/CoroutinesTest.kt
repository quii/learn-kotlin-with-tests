import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isLessThan
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
            job1.join()
        }

        expectThat(calls).isEqualTo(mutableListOf(3, 2, 2, 2, 3))
    }

    private suspend fun load100(): Int {
        delay(100)
        return 100
    }

    @Test
    fun `deferring`() {
        val elapsed = measureTimeMillis {
            runBlocking {
                val data1 = async { load100() }
                val data2 = async { load100() }
                val result1 = data1.await()
                val result2 = data2.await()
                expectThat(result1 + result2).isEqualTo(200)
            }
        }
        expectThat(elapsed).isLessThan(150)
    }

    @Test
    fun `deferring multiple things`() {
        runBlocking {
            val sum = (1..3).map {
                async { load100() }
            }.awaitAll().sum()
            expectThat(sum).isEqualTo(300)
        }
    }

}