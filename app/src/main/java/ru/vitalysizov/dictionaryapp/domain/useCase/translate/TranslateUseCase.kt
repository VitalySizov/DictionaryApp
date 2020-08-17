package ru.vitalysizov.dictionaryapp.domain.useCase.translate

import io.reactivex.Single
import ru.vitalysizov.dictionaryapp.data.repo.ITranslationRepository
import ru.vitalysizov.dictionaryapp.domain.params.translate.TranslateParams
import ru.vitalysizov.dictionaryapp.domain.useCase.base.SingleWithParamsUseCase
import ru.vitalysizov.dictionaryapp.model.domain.WordTranslateItem
import javax.inject.Inject

class TranslateUseCase @Inject constructor(
    private val translationRepository: ITranslationRepository
) : SingleWithParamsUseCase<TranslateParams, List<WordTranslateItem>>() {

    companion object {
        const val CSS_QUERY = "td.trans"
    }

    override fun invoke(params: TranslateParams): Single<List<WordTranslateItem>> {
        return translationRepository.getTranslation(
            query = params.query,
            originLanguage = params.originLanguage.lang,
            resultLanguage = params.resultLanguage.lang
        ).map {
            val translateList = ArrayList<String>()
            val resultList = ArrayList<WordTranslateItem>()
            val elements = it.select(CSS_QUERY)

            elements.forEach { element ->
                val words = element.text()
                val wordList = words.split(";")
                translateList.addAll(wordList)
            }

            translateList.forEachIndexed { index, word ->
                resultList.add(WordTranslateItem(number = index + 1, word = word))
            }

            return@map resultList
        }
    }
}