import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

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

    @Test
    fun `filter map`() {
        val list = listOf(1, 2, 3, 4, 5)
        val doubleLambda = { x: Int -> x * 2 }
        val doubleEvens = list.filter { it % 2 == 0 }.map(doubleLambda)
        assertThat(doubleEvens, equalTo(listOf(4, 8)))
    }

    data class Person(val name: String, val age: Int)
    @Test
    fun `sequences are lazy collections`(){
        val people = listOf(
            Person("Jane", 19),
            Person("Chris", 30),
            Person("Sarah", 31),
            Person("John", 32)
        )

        var under21Calls = 0
        val under21: (Person)->Boolean = { p: Person ->
            under21Calls++
             p.age < 21
        }
        val firstPersonUnder21 = people.asSequence().filter(under21).first()
        assertThat(firstPersonUnder21, equalTo(Person("Jane", 19)))
        assertThat(under21Calls, equalTo(1))

        // with eager
        under21Calls = 0
        val firstPersonUnder21Eager = people.first(under21)
        assertThat(firstPersonUnder21Eager, equalTo(Person("Jane", 19)))
        assertThat(under21Calls, equalTo(4))
    }

    @Test
    fun `type aliases are a nice way to encapsulate collections`() {
        val x = ChrisCollection(listOf(1, 2, 3))
        val y: ChrisCollection = listOf(4, 5, 6)
        val z = listOf(1, 1, 1)
        val dave: List<Int> = listOf(2,2)

        expectThat(x.FancySum).isEqualTo(6)
        expectThat(y.FancySum).isEqualTo(15)
        expectThat(z.FancySum).isEqualTo(3)
        expectThat(dave.FancySum).isEqualTo(4)
    }

    @Test
    fun `fun with maps`() {
        val defaultPrice = 100
        val prices = mapOf("Eggs" to 1, "Bread" to 10).withDefault { defaultPrice }
        expectThat(prices.getValue("Eggs")).isEqualTo(1)
        expectThat(prices.getValue("Bread")).isEqualTo(10)
        expectThat(prices.getValue("Pie")).isEqualTo(defaultPrice)
    }
}

typealias ChrisCollection = List<Int>

fun ChrisCollection(items: List<Int>) = items

val ChrisCollection.FancySum: Int
    get() = this.reduce{acc, i -> acc + i}

fun isAListOf(vararg elements: Int): Matcher<IntRange> = has(IntRange::toList, equalTo(elements.toList()))
