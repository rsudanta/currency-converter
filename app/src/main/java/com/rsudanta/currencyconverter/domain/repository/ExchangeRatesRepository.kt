package com.rsudanta.currencyconverter.domain.repository

import com.rsudanta.currencyconverter.domain.model.ExchangeRates
import com.rsudanta.currencyconverter.util.Resource
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun getExchangeRatesList(
        base: String,
        symbols: String
    ): Flow<Resource<ExchangeRates>>

}