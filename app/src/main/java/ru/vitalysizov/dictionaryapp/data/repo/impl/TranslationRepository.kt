package ru.vitalysizov.dictionaryapp.data.repo.impl

import io.reactivex.Single
import org.jsoup.nodes.Document
import ru.vitalysizov.dictionaryapp.data.remote.network.service.IMultitranService
import ru.vitalysizov.dictionaryapp.data.repo.ITranslationRepository

class TranslationRepository(
    private val multitranService: IMultitranService
) : ITranslationRepository {

    override fun getTranslation(
        query: String,
        originLanguage: String,
        resultLanguage: String
    ): Single<Document> {
        return multitranService.getTranslate(
            query = query,
            originLanguage = originLanguage,
            resultLanguage = resultLanguage
        )
    }
}