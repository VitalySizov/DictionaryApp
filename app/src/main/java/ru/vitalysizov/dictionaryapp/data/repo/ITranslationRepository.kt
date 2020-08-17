package ru.vitalysizov.dictionaryapp.data.repo

import io.reactivex.Single
import org.jsoup.nodes.Document

interface ITranslationRepository {

    fun getTranslation(
        query: String,
        originLanguage: String,
        resultLanguage: String
    ): Single<Document>

}