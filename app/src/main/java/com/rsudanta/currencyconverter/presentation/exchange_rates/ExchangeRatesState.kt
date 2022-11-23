package com.rsudanta.currencyconverter.presentation.exchange_rates

import com.rsudanta.currencyconverter.domain.model.ExchangeRates

data class ExchangeRatesState(
    val isLoading: Boolean = false,
    var exchangeRates: ExchangeRates? = null,
    val error: String = ""
)