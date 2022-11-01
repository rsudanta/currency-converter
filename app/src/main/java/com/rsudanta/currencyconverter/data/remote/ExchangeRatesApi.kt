package com.rsudanta.currencyconverter.data.remote

import com.rsudanta.currencyconverter.data.remote.dto.ConvertDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ExchangeRatesApi {

    @Headers("apikey: $API_KEY")
    @GET("convert")
    suspend fun getConversion(
        @Query("to") to: String?,
        @Query("from") from: String?,
        @Query("amount") amount: Double
    ): Response<ConvertDto>

    companion object {
        const val API_KEY = "NyBQuQva8OXjzM0hwJsVefXIaxSW2yEV"
    }
}
