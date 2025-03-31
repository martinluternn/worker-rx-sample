package com.example.application.ext

import com.example.application.MainApplication
import com.example.core.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

fun MainApplication.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(appModules)
        logger(EmptyLogger())
    }
}