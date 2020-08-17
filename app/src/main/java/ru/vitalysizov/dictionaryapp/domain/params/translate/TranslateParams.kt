package ru.vitalysizov.dictionaryapp.domain.params.translate

data class TranslateParams(
    val query: String,
    val originLanguage: Language,
    val resultLanguage: Language
)