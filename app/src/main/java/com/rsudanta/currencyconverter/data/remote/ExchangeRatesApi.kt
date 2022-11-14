package com.rsudanta.currencyconverter.data.remote

import com.rsudanta.currencyconverter.data.remote.dto.ConvertDto
import com.rsudanta.currencyconverter.data.remote.dto.ExchangeRateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ExchangeRatesApi {

    @Headers("apikey: $API_KEY")
    @GET("convert")
    suspend fun getConversion(
        @Query("to") to: String?,
        @Query("from") from: String?,
        @Query("amount") amount: Double
    ): Response<ConvertDto>

    @Headers("apikey: $API_KEY")
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") base: String,
        @Query("symbols") to: String
    ): Response<ExchangeRateDto>


    companion object {
        const val API_KEY = "aPXFx0CoV6yUOu6ZdZhP0ZxHA9SuYCjY"
    }
}
