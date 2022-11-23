package com.rsudanta.currencyconverter.data.remote.dto

data class ConvertDto(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)