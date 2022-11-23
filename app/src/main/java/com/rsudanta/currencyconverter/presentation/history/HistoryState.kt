package com.rsudanta.currencyconverter.presentation.history

import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.domain.model.History

data class HistoryState(
    val isLoading: Boolean = false,
    var histories: List<History> = emptyList(),
    val error: String = ""
)

