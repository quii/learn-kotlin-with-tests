package anagram

import java.util.*

fun findAnagrams(input: String, words: List<String>): List<String> {
    val wordWithCharactersSorted = sortCharacters(input)
    return words.filter { it.length == input.length }
        .filter { sortCharacters(it) == wordWithCharactersSorted }
}

private fun sortCharacters(word: String) = word.lowercase(Locale.getDefault()).toCharArray().sorted()
