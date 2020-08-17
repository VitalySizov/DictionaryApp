package ru.vitalysizov.dictionaryapp.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ru.vitalysizov.dictionaryapp.App
import ru.vitalysizov.dictionaryapp.di.modules.ActivityBuilder
import ru.vitalysizov.dictionaryapp.di.modules.NetworkModule
import ru.vitalysizov.dictionaryapp.di.modules.RepositoriesModule
import ru.vitalysizov.dictionaryapp.di.modules.ViewModelFactoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelFactoryModule::class,
        ActivityBuilder::class,
        RepositoriesModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun repositoryModule(repositoryModule: RepositoriesModule): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)

}