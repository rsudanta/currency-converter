package com.rsudanta.currencyconverter.data.mapper

import com.rsudanta.currencyconverter.data.remote.dto.ConvertDto
import com.rsudanta.currencyconverter.data.remote.dto.ExchangeRatesDto
import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.domain.model.ExchangeRates

fun ConvertDto.toConvert(): Convert {
    return Convert(info = info, query = query, result = result)
}

fun ExchangeRatesDto.toExchangeRate(): ExchangeRates {
    return ExchangeRates(base = base, rates = rates, timestamp = timestamp)
}