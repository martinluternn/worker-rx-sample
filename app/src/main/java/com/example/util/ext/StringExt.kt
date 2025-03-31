package com.example.util.ext

fun String?.orNA(): String = if (this == null || this.isEmpty()) "N/A" else this
fun String?.orZero(): String = if (this == null || this.isEmpty()) "0" else this