import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.Test

class IterationKtTest {
    @Test
    fun `playing around with lists`() {
        assertThat( List(5) {"a"}, equalTo(listOf("a", "a", "a", "a", "a")))

        val list = listOf(1, 2, 3)
        assertThat(1 in list, equalTo(true))
        assertThat(4 in list, equalTo(false))
    }

    @Test
    fun `mutable lists`() {
        val list = mutableListOf(1, 2, 3)
        assertThat(list, equalTo(listOf(1, 2, 3)))
        list.add(4)
        assertThat(list, equalTo(listOf(1, 2, 3, 4)))

        assertThat(list + 5, equalTo(listOf(1, 2, 3, 4, 5)))
        assertThat(list -1, equalTo(listOf(2, 3, 4)))
    }

    @Test
    fun `it repeats characters`() {
        assertThat(repeatMe('a'), equalTo("aa"))
        assertThat(repeatMe('a', 3), equalTo("aaa"))
    }

    @Test
    fun `int ranges`() {
        assertThat(1..5, isAListOf(1, 2, 3, 4, 5))
        assertThat(1 until 5, isAListOf(1, 2, 3, 4))
        assertThat((1..5 step 2).toList(), equalTo(listOf(1, 3, 5)))
        assertThat((5 downTo 1).toList(), equalTo(listOf(5, 4, 3, 2, 1)))
    }

    @Test
    fun `char ranges`() {
        assertThat(('a'..'e').toList(), equalTo(listOf('a', 'b', 'c', 'd', 'e')))
    }

    @Test
    fun `maps`() {
        val map = mapOf(1 to "one", 2 to "two")
        assertThat(map[1], equalTo("one"))
        assertThat(map[2], equalTo("two"))
    }

    @Test
    fun `sets`() {
        val set = setOf(1, 2, 3)
        assertThat(set, equalTo(setOf(1, 2, 3)))
        assertThat(set + 4, equalTo(setOf(1, 2, 3, 4)))
        assertThat(set - 2, equalTo(setOf(1, 3)))
        assertThat(1 in set, equalTo(true))
        assertThat(4 in set, equalTo(false))
    }
}

fun isAListOf(vararg elements: Int): Matcher<IntRange> = has(IntRange::toList, equalTo(elements.toList()))