package ru.vitalysizov.dictionaryapp.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.vitalysizov.dictionaryapp.presentation.AppActivity
import ru.vitalysizov.dictionaryapp.presentation.base.BaseViewModel
import ru.vitalysizov.dictionaryapp.presentation.search.viewmodel.SearchViewModel


@Module
interface ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            ActivityModule::class,
            FragmentBuilder::class
        ]
    )
    fun contributeAppActivity(): AppActivity


    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}