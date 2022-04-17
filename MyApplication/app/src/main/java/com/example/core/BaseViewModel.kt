package com.example.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    open val shouldShowError: MutableLiveData<Any> = MutableLiveData()
    open val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    open fun handleError(e: Throwable) {
        shouldShowError.value = e
    }

}