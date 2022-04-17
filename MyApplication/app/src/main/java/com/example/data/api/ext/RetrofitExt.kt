package com.example.data.api.ext

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun initRetrofit(url: String?, gson: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder().apply {
        url?.also { baseUrl(it) }
        client(client)
        addConverterFactory(GsonConverterFactory.create(gson))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }.build()
}