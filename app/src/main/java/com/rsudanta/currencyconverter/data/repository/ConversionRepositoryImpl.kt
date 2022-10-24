package com.rsudanta.currencyconverter.data.repository

import com.google.gson.Gson
import com.rsudanta.currencyconverter.data.mapper.toConvert
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.remote.dto.ErrorResponse
import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi
) : ConversionRepository {

    override suspend fun getConversion(
        to: String,
        from: String,
        amount: Double
    ): Flow<Resource<Convert>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            try {
                val response = api.getConversion(to = to, from = from, amount = amount)
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()?.toConvert()))
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
                emit(Resource.Error(message = "No internet connection"))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}