package com.rsudanta.currencyconverter.data.repository

import com.google.gson.Gson
import com.rsudanta.currencyconverter.data.mapper.toExchangeRate
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.remote.dto.ErrorResponse
import com.rsudanta.currencyconverter.domain.model.ExchangeRates
import com.rsudanta.currencyconverter.domain.repository.ExchangeRatesRepository
import com.rsudanta.currencyconverter.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi
) : ExchangeRatesRepository {
    override suspend fun getExchangeRates(
        base: String,
        symbols: String
    ): Flow<Resource<ExchangeRates>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            try {
                val response = api.getExchangeRates(base = base, symbols = symbols)
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()?.toExchangeRate()))
                    emit(Resource.Loading(isLoading = false))
                } else {
                    val gson = Gson()
                    val errorResponse: ErrorResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        ErrorResponse::class.java
                    )
                    emit(Resource.Error(message = errorResponse.error.message))
                    emit(Resource.Loading(isLoading = false))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Unable to get response from server"))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}