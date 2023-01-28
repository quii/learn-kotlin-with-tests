fun repeatMe(char: Char, count: Int = 2): String {
    // return (0..count).toList().fold("") { acc, i -> acc + char }
    // char.toString().repeat(count)
    return List(count) { char }.joinToString("")
}