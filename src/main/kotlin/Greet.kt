fun greet(lang: Language = Language.ENGLISH, name: String = "World") = "${lang.greeting}, $name!"

enum class Language(val greeting: String) {
    ENGLISH("Hello"),
    FRENCH("Bonjour")
}
