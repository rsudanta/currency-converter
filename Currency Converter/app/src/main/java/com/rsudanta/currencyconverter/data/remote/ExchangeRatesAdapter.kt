package com.rsudanta.currencyconverter.data.remote

import com.rsudanta.currencyconverter.data.remote.dto.ExchangeRatesDto
import com.rsudanta.currencyconverter.data.remote.dto.Rate
import com.squareup.moshi.*
import java.lang.UnsupportedOperationException

class ExchangeRatesAdapter : JsonAdapter<ExchangeRatesDto>() {
    @FromJson
    override fun fromJson(reader: JsonReader): ExchangeRatesDto? {
        reader.beginObject()
        var base = ""
        var date = ""
        val rates = mutableListOf<Rate>()
        var success = true
        var timestamp = 0

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "base" -> base = reader.nextString()
                "date" -> date = reader.nextString()
                "rates" -> {
                    reader.beginObject()
                    while (reader.peek() != JsonReader.Token.END_OBJECT) {
                        val code = reader.nextName()
                        val rate = when (code) {
                            base -> reader.nextInt().toDouble()
                            else -> reader.nextDouble()
                        }

                        rates.add(
                            Rate(
                                code = code,
                                rate = rate,
                            )
                        )
                    }
                    reader.endObject()
                }
                "success" -> success = reader.nextBoolean()
                "timestamp" -> timestamp = reader.nextInt()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return ExchangeRatesDto(
            base = base,
            date = date,
            rates = rates,
            success = success,
            timestamp = timestamp
        )
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: ExchangeRatesDto?) {
        throw UnsupportedOperationException()
    }
}