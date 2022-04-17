package com.example.data.api.ext

import com.example.application.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

fun initOkHttpClient(
    isDebug: Boolean,
    interceptors: Set<Interceptor>,
    debugInterceptor: Set<Interceptor>
): OkHttpClient =
    OkHttpClient.Builder().apply {
        interceptors.forEach { addInterceptor(it) }
        if (isDebug) debugInterceptor.forEach { addInterceptor(it) }
        connectTimeout(8, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()

val HEADER_CONTENT_TYPE = Pair("Content-Type", "application/json")
val HEADER_AGENT = Pair("User-Agent", "android-${BuildConfig.VERSION_NAME}")
