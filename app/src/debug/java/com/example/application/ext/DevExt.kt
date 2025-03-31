package com.example.application.ext

import com.chuckerteam.chucker.api.Chucker
import com.example.application.BuildConfig
import com.example.application.MainApplication
import org.koin.android.ext.android.get

fun MainApplication.initChuck() {
    if (BuildConfig.DEBUG) {
        Chucker.registerDefaultCrashHandler(get())
    }
}