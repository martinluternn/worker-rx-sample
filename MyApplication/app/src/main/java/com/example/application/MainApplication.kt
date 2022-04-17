package com.example.application

import android.app.Application
import com.example.application.ext.initChuck
import com.example.application.ext.initKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initChuck()
    }
}