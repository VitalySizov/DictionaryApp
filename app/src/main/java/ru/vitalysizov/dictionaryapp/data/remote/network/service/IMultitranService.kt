package ru.vitalysizov.dictionaryapp.data.remote.network.service

import io.reactivex.Single
import org.jsoup.nodes.Document

interface IMultitranService {

    fun getTranslate(
        query: String,
        originLanguage: String,
        resultLanguage: String
    ): Single<Document>

}