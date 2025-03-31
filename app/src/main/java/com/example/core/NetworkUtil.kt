package com.example.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission

class NetworkUtil
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(private val application: Application) {
    fun isOnline(): Boolean {
        val netInfo =
            (application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}