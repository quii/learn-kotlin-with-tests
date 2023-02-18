package anagram

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains

class AnagramTest {
    @Test
    fun `find dog as an anagram of god`() {
        val anagrams = findAnagrams("god", words)
        expectThat(anagrams).contains("dog")
    }

    @Test
    fun `multiword anagrams`() {
        val input = "A CAT"
        expectThat(findAnagrams(input, words)).contains("Act")
    }
}