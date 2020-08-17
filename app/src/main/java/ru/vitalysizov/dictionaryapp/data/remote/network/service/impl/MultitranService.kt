package ru.vitalysizov.dictionaryapp.data.remote.network.service.impl

import android.net.Uri
import io.reactivex.Single
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.vitalysizov.dictionaryapp.BuildConfig
import ru.vitalysizov.dictionaryapp.data.remote.network.service.IMultitranService

class MultitranService : IMultitranService {

    companion object {
        private const val QUERY = "s"
        private const val ORIGIN_LANGUAGE_PARAMS = "l1"
        private const val RESULT_LANGUAGE_PARAMS = "l2"
    }

    override fun getTranslate(
        query: String,
        originLanguage: String,
        resultLanguage: String
    ): Single<Document> {
        return Single.fromCallable {
            val url = Uri.parse(BuildConfig.BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY, query)
                .appendQueryParameter(ORIGIN_LANGUAGE_PARAMS, originLanguage)
                .appendQueryParameter(RESULT_LANGUAGE_PARAMS, resultLanguage)
                .build()
                .toString()
            return@fromCallable Jsoup.connect(url).get()
        }
    }
}