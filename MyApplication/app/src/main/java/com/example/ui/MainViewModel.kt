package com.example.ui

import androidx.lifecycle.MutableLiveData
import com.example.core.BaseViewModel
import com.example.core.ext.SingleIoThreadScheduler
import com.example.core.ext.disposedBy
import com.example.data.DataModel
import com.example.data.MainRepository
import com.example.util.Const
import com.example.util.ext.orNA
import com.example.util.ext.toDateString
import io.reactivex.observers.DisposableSingleObserver

class MainViewModel(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val shouldShowData: MutableLiveData<Pair<String, List<DataModel>>> = MutableLiveData()
    private var dataModels: List<DataModel> = listOf()

    fun onViewLoaded() {
        shouldShowLoading.value = true
        getData()
    }

    private fun getData() {
        mainRepository.getLatestData()
            .compose(SingleIoThreadScheduler())
            .subscribeWith(LatestDataObserver())
            .disposedBy(disposable)
    }

    inner class LatestDataObserver : DisposableSingleObserver<List<DataModel>>() {
        override fun onSuccess(t: List<DataModel>) {
            shouldShowLoading.value = false
            dataModels = t
            shouldShowData.value =
                t.firstOrNull()?.time?.updatedISO?.toDateString(Const.APP_DAY_TITLE).orNA() to t
        }

        override fun onError(e: Throwable) {
            shouldShowLoading.value = false
            shouldShowError.value = e
        }
    }
}