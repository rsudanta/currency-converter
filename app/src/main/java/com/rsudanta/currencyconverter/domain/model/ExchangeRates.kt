package com.rsudanta.currencyconverter.domain.model

import com.rsudanta.currencyconverter.data.remote.dto.Rates
import java.sql.Timestamp

data class ExchangeRates(
    val base: String,
    val rates: Rates,
    val timestamp: Int
)