package com.example.data

import androidx.room.EmptyResultSetException
import com.example.data.api.MainAPI
import com.example.data.api.base.GetData
import com.example.data.local.DataCache
import com.example.data.local.MainDatabase
import com.example.data.mapper.*
import io.reactivex.Single
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainRepository(
    private val api: MainAPI,
    private val db: MainDatabase
) {
    fun getData(): Single<DataModel> {
        return api.getData().map(GetData())
            .map(DataAPIToModelMapper())
    }

    fun getLatestData(limit: Int = 5): Single<List<DataModel>> {
        val callable: Callable<List<DataCache>> =
            Callable { db.mainDao().getLatestData(limit) }
        val future: Future<List<DataCache>> =
            Executors.newCachedThreadPool().submit(callable)
        return future.get()?.let { Single.just(DataCacheListToModelMapper().apply(it)) }
            ?: Single.error(EmptyResultSetException("data is null"))
    }

    fun getDataWithDate(startDate: String, endDate: String): Single<List<DataModel>> {
        val callable: Callable<List<DataCache>> =
            Callable { db.mainDao().getDataWithDate(startDate, endDate) }
        val future: Future<List<DataCache>> =
            Executors.newCachedThreadPool().submit(callable)
        return future.get()?.let { Single.just(DataCacheListToModelMapper().apply(it)) }
            ?: Single.error(EmptyResultSetException("data is null"))
    }

    fun saveData(body: DataModel): Single<Unit> {
        val cache = DataModelToCacheMapper().apply(body)
        val callable: Callable<Unit> = Callable { db.mainDao().insertData(cache) }
        val future: Future<Unit> = Executors.newCachedThreadPool().submit(callable)
        return Single.just(future.get())
    }

    fun updateLatLong(id: String, lat: String, long: String) {
        val callable: Callable<Unit> = Callable { db.mainDao().updateLatLong(id, lat, long) }
        val future: Future<Unit> = Executors.newCachedThreadPool().submit(callable)
        return future.get()
    }

    fun removeAllData(dataModel: List<DataModel>): Single<Unit> {
        val cache = DataModelListToCacheMapper().apply(dataModel)
        val callable: Callable<Unit> = Callable { db.mainDao().removeAllData(cache) }
        val future: Future<Unit> = Executors.newCachedThreadPool().submit(callable)
        return Single.just(future.get())
    }
}