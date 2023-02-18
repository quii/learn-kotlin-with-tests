package anagram

import java.io.File

val words: List<String> by lazy {
    File("/usr/share/dict/words").readLines()
}