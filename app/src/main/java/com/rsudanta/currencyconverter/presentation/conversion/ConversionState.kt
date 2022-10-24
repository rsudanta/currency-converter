package com.rsudanta.currencyconverter.presentation.conversion

import com.rsudanta.currencyconverter.domain.model.Convert

data class ConversionState(
    val isLoading: Boolean = false,
    var convert: Convert? = null,
    val error: String = ""
)
