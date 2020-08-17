package ru.vitalysizov.dictionaryapp.presentation.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vitalysizov.dictionaryapp.R
import ru.vitalysizov.dictionaryapp.domain.params.translate.Language
import ru.vitalysizov.dictionaryapp.domain.params.translate.TranslateParams
import ru.vitalysizov.dictionaryapp.domain.useCase.translate.TranslateUseCase
import ru.vitalysizov.dictionaryapp.model.domain.CurrentQueryAndLang
import ru.vitalysizov.dictionaryapp.model.domain.WordTranslateItem
import ru.vitalysizov.dictionaryapp.presentation.base.BaseViewModel
import ru.vitalysizov.dictionaryapp.utils.Event
import ru.vitalysizov.dictionaryapp.utils.ioToUi
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase
) : BaseViewModel() {

    val originalLanguage = MutableLiveData<Int>()
    val choiceOriginalLanguage: LiveData<Int>
        get() = originalLanguage

    val resultLanguage = MutableLiveData<Int>()
    val choiceResultLanguage: LiveData<Int>
        get() = resultLanguage

    private val _currentQuery = MutableLiveData<String>()
    private val currentQuery: LiveData<String>
        get() = _currentQuery

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _translateResult = MutableLiveData<List<WordTranslateItem>>()
    val translateResult: LiveData<List<WordTranslateItem>>
        get() = _translateResult

    private val _currentQueryWithLanguage = MutableLiveData<CurrentQueryAndLang>()
    val currentQueryWithLanguage: LiveData<CurrentQueryAndLang>
        get() = _currentQueryWithLanguage

    fun getTranslate() {
        val originalLang = Language.getLanguage(originalLanguage.value)
        val resultLang = Language.getLanguage(resultLanguage.value)

        val latRegex = Regex(pattern = "[a-zA-Z]")
        val cyrRegex = Regex(pattern = "[а-яА-Я]")

        if (currentQuery.value.isNullOrEmpty()) {
            messageMutable.value = Event(R.string.error_empty_text)
            return
        }

        if (originalLang == Language.EMPTY) {
            messageMutable.value = Event(R.string.error_choice_original_language)
            return
        }

        if (resultLang == Language.EMPTY) {
            messageMutable.value = Event(R.string.error_choice_result_language)
            return
        }

        when (originalLang) {
            Language.RUS -> {
                if (latRegex.containsMatchIn(currentQuery.value.orEmpty())) {
                    messageMutable.value = Event(R.string.error_need_cyrillic)
                    return
                }
            }
            else -> {
                if (cyrRegex.containsMatchIn(currentQuery.value.orEmpty())) {
                    messageMutable.value = Event(R.string.error_need_lat)
                    return
                }
            }
        }

        if (isInternetAvailable.value == false) {
            messageMutable.value = Event(R.string.error_no_connection)
            return
        }

        val params = TranslateParams(
            query = currentQuery.value.orEmpty(),
            originLanguage = originalLang,
            resultLanguage = resultLang
        )

        _currentQueryWithLanguage.value =
            CurrentQueryAndLang(
                query = currentQuery.value.orEmpty(),
                language = Language.getStringLanguage(resultLanguage.value)
            )

        launch {
            showLoading()
            translateUseCase.invoke(params)
                .ioToUi()
                .subscribe({
                    hideLoading()
                    handleSuccessTranslate(it)
                }, { handleError(it) })
        }
    }

    private fun handleSuccessTranslate(translationList: List<WordTranslateItem>) {
        _isEmpty.value = translationList.isEmpty()
        _translateResult.value = translationList
    }

    fun setSearchQuery(query: String?) {
        _currentQuery.value = query
    }

}