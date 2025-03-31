package com.example.data.api

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("time")
    val time: TimeResponse? = null,
    @SerializedName("disclaimer")
    val disclaimer: String? = null,
    @SerializedName("chartName")
    val chartName: String? = null,
    @SerializedName("bpi")
    val bpi: BPIResponse? = null
)

data class TimeResponse(
    @SerializedName("updated")
    val updated: String? = null,
    @SerializedName("updatedISO")
    val updatedISO: String? = null,
    @SerializedName("updateduk")
    val updatedUK: String? = null
)

data class BPIResponse(
    @SerializedName("USD")
    val usd: CurrencyResponse? = null,
    @SerializedName("GBP")
    val gbp: CurrencyResponse? = null,
    @SerializedName("EUR")
    val eur: CurrencyResponse? = null
)

data class CurrencyResponse(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("rate")
    val rate: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("rate_float")
    val rateFloat: Double? = 0.0
)