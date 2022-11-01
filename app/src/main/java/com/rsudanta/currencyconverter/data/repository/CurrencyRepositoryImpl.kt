package com.rsudanta.currencyconverter.data.repository

import androidx.compose.runtime.currentComposer
import com.rsudanta.currencyconverter.data.local.dao.CurrencyDao
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val dao: CurrencyDao) :
    CurrencyRepository {
    override suspend fun getCurrencies(): Flow<List<Currency>> {
        return dao.getCurrencies()
    }

    override suspend fun getCurrencyById(id: Int): Flow<Currency> {
        return dao.getCurrencyById(id = id)
    }

    override suspend fun insertCurrency(currency: Currency) {
        dao.insertCurrency(currency = currency)
    }
}