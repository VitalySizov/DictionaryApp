package ru.vitalysizov.dictionaryapp.di.modules

import dagger.Module
import dagger.Provides
import ru.vitalysizov.dictionaryapp.data.remote.network.service.IMultitranService
import ru.vitalysizov.dictionaryapp.data.repo.ITranslationRepository
import ru.vitalysizov.dictionaryapp.data.repo.impl.TranslationRepository

import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideTranslationRepository(service: IMultitranService): ITranslationRepository {
        return TranslationRepository(service)
    }

}