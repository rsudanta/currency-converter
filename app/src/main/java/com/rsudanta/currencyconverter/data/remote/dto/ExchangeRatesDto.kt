package com.rsudanta.currencyconverter.data.remote.dto

data class ExchangeRatesDto(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)