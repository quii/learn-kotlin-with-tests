package anagram

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class LoaderTest {
    @Test
    fun `load words from file`() {
        expectThat(words).isA<List<String>>().isNotEmpty().contains("aardvark", "zygote")
    }
}

