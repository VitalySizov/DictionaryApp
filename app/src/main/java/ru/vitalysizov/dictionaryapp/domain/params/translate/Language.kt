package ru.vitalysizov.dictionaryapp.domain.params.translate

enum class Language(val lang: String) {
    EMPTY("0"),
    ENG("1"),
    RUS("2"),
    GER("3"),
    FRE("4");

    companion object {

        private fun from(s: String): Language? = values().find { it.lang == s }

        fun getLanguage(lang: Int?): Language {
            return from(lang.toString()) ?: EMPTY
        }

        fun getStringLanguage(lang: Int?): String {
            return from(lang.toString())?.name.orEmpty()
        }
    }
}