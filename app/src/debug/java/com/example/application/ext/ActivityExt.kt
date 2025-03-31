package com.example.application.ext

import android.app.Activity
import com.chuckerteam.chucker.api.ChuckerCollector
import org.koin.android.ext.android.get

fun Activity.chuckerLogError(throwable: Throwable) {
    get<ChuckerCollector>().onError(this::class.java.simpleName, throwable)
}