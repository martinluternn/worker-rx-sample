package com.example.util.ext

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.view.View
import androidx.core.content.ContextCompat
import com.example.application.R
import com.example.application.ext.chuckerLogError
import com.example.data.api.base.CommonError
import com.example.data.api.base.InternalServerError
import com.google.android.material.snackbar.Snackbar
import java.net.ConnectException

fun Activity.showSnackbar(
    message: String = getString(R.string.label_connection_problem),
    rootView: View = findViewById(android.R.id.content),
    duration: Int = Snackbar.LENGTH_SHORT,
    drawables: Int = R.drawable.shape_red_round
) {
    Snackbar.make(
        rootView,
        message,
        duration
    ).apply {
        view.setBackgroundResource(drawables)
        setActionTextColor(ContextCompat.getColor(context, R.color.white))
    }.show()
}

fun Activity.isLocationDisable(): Boolean {
    val manager: LocationManager =
        this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Activity.handleCommonError(
    e: Throwable,
    onErrorReturn: ((Throwable) -> Unit)? = null,
    commonError: ((CommonError) -> Unit)? = null
) {
    chuckerLogError(e)
    e.printStackTrace()
    when (e) {
        is ConnectException -> showSnackbar(
            message = getString(R.string.label_connection_problem),
            drawables = R.drawable.shape_red_round
        )
        is InternalServerError -> showSnackbar(getString(R.string.internal_server_error))
        else -> onErrorReturn?.invoke(e) ?: showSnackbar(e.message ?: defaultErrorMessage())
    }
}

fun Context.defaultErrorMessage(): String = getString(R.string.label_connection_problem)