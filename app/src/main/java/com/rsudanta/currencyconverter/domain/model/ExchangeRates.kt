package com.rsudanta.currencyconverter.domain.model

import com.rsudanta.currencyconverter.data.remote.dto.Rate

data class ExchangeRates(
    val base: String,
    val rates: List<Rate>,
    val timestamp: Int
)