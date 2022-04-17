package com.example.data.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MainAPI {
    companion object {
        const val PATH_DATA = "v1/bpi/currentprice.json"
    }

    @GET(PATH_DATA)
    fun getData(): Single<Response<DataResponse>>
}