package ru.vitalysizov.dictionaryapp.di.modules

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import dagger.Binds
import dagger.Module

@Module
interface ActivityModule {

    @Binds
    fun provideActivity(activity: AppCompatActivity): Activity

    @Binds
    fun provideLifecycleOwner(activity: AppCompatActivity): LifecycleOwner
}