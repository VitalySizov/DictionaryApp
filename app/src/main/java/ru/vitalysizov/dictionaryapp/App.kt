package ru.vitalysizov.dictionaryapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

import ru.vitalysizov.dictionaryapp.di.components.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}