package ru.vitalysizov.dictionaryapp.presentation.base

import android.view.View
import androidx.databinding.BindingAdapter
import ru.vitalysizov.dictionaryapp.utils.visibleOrGone

@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    this.visibleOrGone(visible)
}
