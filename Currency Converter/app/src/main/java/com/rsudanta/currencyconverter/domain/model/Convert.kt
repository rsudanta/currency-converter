package com.rsudanta.currencyconverter.domain.model

import com.rsudanta.currencyconverter.data.remote.dto.Info
import com.rsudanta.currencyconverter.data.remote.dto.Query

data class Convert(
    val info: Info,
    val query: Query,
    val result: Double,
)
