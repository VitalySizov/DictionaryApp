package ru.vitalysizov.dictionaryapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.vitalysizov.dictionaryapp.BuildConfig
import ru.vitalysizov.dictionaryapp.utils.Event
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    protected val messageMutable = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>>
        get() = messageMutable

    val isInternetAvailableMutable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean>
        get() = isInternetAvailableMutable

    private val compositeDisposable = CompositeDisposable()

    fun launch(disposable: () -> Disposable) {
        compositeDisposable.add(disposable.invoke())
    }

    fun handleError(t: Throwable) {
        if (BuildConfig.DEBUG) {
            hideLoading()
            t.printStackTrace()
        }
    }

    fun showLoading() {
        loading.value = true
    }

    fun hideLoading() {
        loading.value = false
    }

}