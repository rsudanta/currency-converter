package com.rsudanta.currencyconverter.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.timestampToDate(): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy")
    val netDate = Date(this.toLong() * 1000L)
    return sdf.format(netDate)
}