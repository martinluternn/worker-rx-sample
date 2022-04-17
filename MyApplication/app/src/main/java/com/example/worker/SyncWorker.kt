package com.example.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.core.ext.SingleIoThreadScheduler
import com.example.data.DataModel
import com.example.data.MainRepository
import com.example.util.LOCATION_REQUEST
import com.example.util.ext.endedDate
import com.example.util.ext.startingDate
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class SyncWorker(val context: Context, workerParameters: WorkerParameters) :
    RxWorker(context, workerParameters), KoinComponent {

    companion object {
        private const val CURRENCY_SYNC_TAG = "CURRENCY_SYNC_TAG"

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequest
            .Builder(SyncWorker::class.java)
            .setInitialDelay(60L, TimeUnit.MINUTES)
            .addTag(CURRENCY_SYNC_TAG)
            .setConstraints(constraints)
            .build()

        fun start(context: Context) {
            try {
                WorkManager.getInstance(context)
                    .enqueueUniqueWork(
                        CURRENCY_SYNC_TAG, ExistingWorkPolicy.KEEP, workRequest
                    )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val mainRepository: MainRepository by inject()
    private var dataModel: DataModel? = null
    private lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    private fun requestLocationChanged() {
        locationManager =
            context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REQUEST.MIN_TIME,
            LOCATION_REQUEST.MIN_REQUEST
        ) {
            var lat = ""
            var long = ""
            try {
                lat = it.latitude.toString()
                long = it.longitude.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            dataModel?.let {
                mainRepository.updateLatLong(it.id.orEmpty(), lat, long)
            }
            start(context)
        }
    }

    private fun removeOutDateData(): Single<Unit> {
        return mainRepository.getDataWithDate(startingDate(), endedDate())
            .flatMap {
                mainRepository.removeAllData(it)
            }
    }

    override fun createWork(): Single<Result> {
        return mainRepository.getData().flatMap {
            dataModel = it.apply { id = UUID.randomUUID().toString() }
            mainRepository.saveData(it).flatMap {
                removeOutDateData().flatMap {
                    Single.just(Result.success())
                }
            }
        }.compose(SingleIoThreadScheduler()).doAfterSuccess {
            /**
             * latitude and longitude will update when the app is in the foreground
             */
            requestLocationChanged()
        }
    }
}
