package com.example.data.mapper

import com.example.data.BPIModel
import com.example.data.CurrencyModel
import com.example.data.DataModel
import com.example.data.TimeModel
import com.example.data.api.DataResponse
import com.example.data.local.DataCache
import com.google.gson.Gson
import io.reactivex.functions.Function
import java.util.*

class DataAPIToModelMapper :
    Function<DataResponse, DataModel> {
    override fun apply(t: DataResponse): DataModel {
        return DataModel(
            time = TimeModel(
                updated = t.time?.updated,
                updatedISO = t.time?.updatedISO,
                updatedUK = t.time?.updatedUK
            ),
            chartName = t.chartName,
            disclaimer = t.disclaimer,
            bpi = BPIModel(
                usd = CurrencyModel(
                    code = t.bpi?.usd?.code,
                    symbol = t.bpi?.usd?.symbol,
                    rate = t.bpi?.usd?.rate,
                    description = t.bpi?.usd?.description,
                    rateFloat = t.bpi?.usd?.rateFloat
                ),
                eur = CurrencyModel(
                    code = t.bpi?.eur?.code,
                    symbol = t.bpi?.eur?.symbol,
                    rate = t.bpi?.eur?.rate,
                    description = t.bpi?.eur?.description,
                    rateFloat = t.bpi?.eur?.rateFloat
                ),
                gbp = CurrencyModel(
                    code = t.bpi?.gbp?.code,
                    symbol = t.bpi?.gbp?.symbol,
                    rate = t.bpi?.gbp?.rate,
                    description = t.bpi?.gbp?.description,
                    rateFloat = t.bpi?.gbp?.rateFloat
                )
            )
        )
    }
}

class DataCacheListToModelMapper :
    Function<List<DataCache>, List<DataModel>> {
    override fun apply(t: List<DataCache>): List<DataModel> {
        return t.map { DataCacheToModelMapper().apply(it) }
    }
}

class DataCacheToModelMapper :
    Function<DataCache, DataModel> {
    override fun apply(t: DataCache): DataModel {
        return DataModel(
            id = t.id,
            time = TimeModel(
                updated = t.updatedTime,
                updatedISO = t.updatedTimeISO,
                updatedUK = t.updatedTimeUK
            ),
            chartName = t.name,
            disclaimer = t.disclaimer,
            bpi = Gson().fromJson(t.bpi, BPIModel::class.java),
            long = t.longitude,
            lat = t.latitude
        )
    }
}

class DataModelListToCacheMapper :
    Function<List<DataModel>, List<DataCache>> {
    override fun apply(t: List<DataModel>): List<DataCache> {
        return t.map { DataModelToCacheMapper().apply(it) }
    }
}

class DataModelToCacheMapper :
    Function<DataModel, DataCache> {
    override fun apply(t: DataModel): DataCache {
        return DataCache(
            id = t.id ?: UUID.randomUUID().toString(),
            updatedTime = t.time?.updated.orEmpty(),
            updatedTimeISO = t.time?.updatedISO.orEmpty(),
            updatedTimeUK = t.time?.updatedUK.orEmpty(),
            name = t.chartName.orEmpty(),
            disclaimer = t.disclaimer.orEmpty(),
            latitude = t.lat.orEmpty(),
            longitude = t.long.orEmpty(),
            bpi = Gson().toJson(t.bpi)
        )
    }
}