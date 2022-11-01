package com.rsudanta.currencyconverter.domain.repository

import com.rsudanta.currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencies(): Flow<List<Currency>>

    suspend fun getCurrencyById(id: Int): Flow<Currency>

    suspend fun insertCurrency(currency: Currency)
}