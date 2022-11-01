package com.rsudanta.currencyconverter.domain.repository

import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.util.Resource
import kotlinx.coroutines.flow.Flow

interface ConversionRepository {
    suspend fun getConversion(to: String?, from: String?, amount: Double): Flow<Resource<Convert>>
}