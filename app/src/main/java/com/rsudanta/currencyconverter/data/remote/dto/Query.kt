package com.rsudanta.currencyconverter.data.remote.dto

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)