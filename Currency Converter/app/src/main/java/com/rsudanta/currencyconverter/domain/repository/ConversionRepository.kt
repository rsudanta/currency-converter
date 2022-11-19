package com.rsudanta.currencyconverter.domain.repository

import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.util.Resource
import kotlinx.coroutines.flow.Flow

interface ConversionRepository {
    suspend fun getConversion(to: String?, from: String?, amount: Double): Flow<Resource<Convert>>

    suspend fun persistConvertFromState(currency: Currency)

    suspend fun readConvertFromState(): Flow<String?>

    suspend fun persistConvertToState(currency: Currency)

    suspend fun readConvertToState(): Flow<String?>
}