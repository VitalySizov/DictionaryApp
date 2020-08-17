package ru.vitalysizov.dictionaryapp.di.modules

import dagger.Module
import dagger.Provides
import ru.vitalysizov.dictionaryapp.data.remote.network.service.IMultitranService
import ru.vitalysizov.dictionaryapp.data.remote.network.service.impl.MultitranService
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMultitranService(): IMultitranService {
        return MultitranService()
    }

}