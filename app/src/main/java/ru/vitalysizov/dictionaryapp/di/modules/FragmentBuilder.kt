package ru.vitalysizov.dictionaryapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.vitalysizov.dictionaryapp.presentation.search.view.SearchFragment

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

}