package com.example.util.ext

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.onRequestPermission(
    vararg permissions: String,
    requestCode: Int = 12345,
    callback: PermissionCallback.() -> Unit
) {
    val permissionCallback = PermissionCallback().apply(callback)
    ActivityCompat.requestPermissions(
        this,
        permissions,
        PermissionMapper.putCallback(requestCode, permissionCallback)
    )
}

fun Activity.handleRequestPermission(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    val callback = PermissionMapper.getCallback(requestCode)
    val needPermission = filterNeedPermission(permissions)
    if (needPermission.isNotEmpty()) {
        if (needPermission.isPermissionGranted(grantResults)) {
            callback?.onGranted?.invoke()
        } else {
            if (isNotPermanentDenied(needPermission)) {
                callback?.onDenied?.invoke()
            } else {
                callback?.onPermanentDenied?.invoke()
            }
        }
    } else {
        callback?.onGranted?.invoke()
    }
}

fun Activity.isNotPermanentDenied(listPermission: List<String>): Boolean {
    var isPermanent: Boolean = false
    listPermission.map {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) {
            isPermanent = true
        }
    }
    return isPermanent
}

fun Context.filterNeedPermission(permission: Array<out String>) =
    permission
        .toList()
        .filter {
            checkCompatPermission(it)
        }

fun Context.checkCompatPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) != PackageManager.PERMISSION_GRANTED

fun List<String>.isPermissionGranted(grantResults: IntArray) =
    size == grantResults.size &&
            grantResults.none {
                it == PackageManager.PERMISSION_DENIED
            }

object PermissionMapper {
    private val callbackArray: SparseArray<PermissionCallback> = SparseArray()

    fun putCallback(
        requestCode: Int,
        callback: PermissionCallback
    ): Int {
        callbackArray.put(requestCode, callback)
        return requestCode
    }

    fun getCallback(requestCode: Int): PermissionCallback? {
        return callbackArray[requestCode]?.also {
            callbackArray.remove(requestCode)
        }
    }
}

class PermissionCallback {
    internal var onGranted: (() -> Unit)? = null
    internal var onDenied: (() -> Unit)? = null
    internal var onPermanentDenied: (() -> Unit)? = null

    fun onGranted(onGranted: () -> Unit) {
        this.onGranted = onGranted
    }

    fun onDenied(onDenied: () -> Unit) {
        this.onDenied = onDenied
    }

    fun onPermanentDenied(onPermanentDenied: () -> Unit) {
        this.onPermanentDenied = onPermanentDenied
    }
}