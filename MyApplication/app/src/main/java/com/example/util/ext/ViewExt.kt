package com.example.util.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.ui.loading.ProgressDialog

fun ProgressDialog?.initLoading(context: Context): ProgressDialog {
    if (this == null)
        return ProgressDialog(context)
    return this
}

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val safeClickListener = SingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(safeClickListener)
}

class SingleClickListener(
    private var interval: Int = 800,
    private val onSingleClick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < interval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleClick(v)
    }
}

class DiffUtilCallbackEquals<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}