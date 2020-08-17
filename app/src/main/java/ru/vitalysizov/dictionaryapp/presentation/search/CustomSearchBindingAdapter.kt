package ru.vitalysizov.dictionaryapp.presentation.search

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.vitalysizov.dictionaryapp.R
import ru.vitalysizov.dictionaryapp.model.domain.CurrentQueryAndLang
import ru.vitalysizov.dictionaryapp.utils.gone
import ru.vitalysizov.dictionaryapp.utils.visible

@BindingAdapter(value = ["setCurrentQueryAndLanguage"])
fun TextView.setCurrentQueryAndLanguage(currentQueryAndLang: CurrentQueryAndLang?) {
    if (currentQueryAndLang?.query == null || currentQueryAndLang.language == null) {
        this.gone()
    } else {
        this.visible()
        this.text = resources.getString(
            R.string.item_current_query_and_language_mask,
            currentQueryAndLang.query, currentQueryAndLang.language
        )
    }
}