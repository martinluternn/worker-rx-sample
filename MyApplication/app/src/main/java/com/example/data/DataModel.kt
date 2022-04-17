package com.example.data

data class DataModel(
    var id: String? = null,
    val time: TimeModel? = null,
    val disclaimer: String? = null,
    val chartName: String? = null,
    val bpi: BPIModel? = null,
    var lat: String? = null,
    var long: String? = null
)

data class TimeModel(
    val updated: String? = null,
    val updatedISO: String? = null,
    val updatedUK: String? = null
)

data class BPIModel(
    val usd: CurrencyModel? = null,
    val gbp: CurrencyModel? = null,
    val eur: CurrencyModel? = null
)

data class CurrencyModel(
    val code: String? = null,
    val symbol: String? = null,
    val rate: String? = null,
    val description: String? = null,
    val rateFloat: Double? = 0.0
)