package ru.vitalysizov.dictionaryapp.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.vitalysizov.dictionaryapp.R
import ru.vitalysizov.dictionaryapp.presentation.base.BaseActivity
import javax.inject.Inject

class AppActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}