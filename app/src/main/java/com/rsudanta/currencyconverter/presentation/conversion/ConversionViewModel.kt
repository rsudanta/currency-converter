package com.rsudanta.currencyconverter.presentation.conversion

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.domain.model.History
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.domain.repository.HistoryRepository
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.util.Resource
import com.rsudanta.currencyconverter.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val conversionRepository: ConversionRepository,
    private val historyRepository: HistoryRepository,
    private val sharedViewModel: SharedViewModel
) :
    ViewModel() {

    var conversionState = mutableStateOf(ConversionState())
        private set

    var convertFrom = mutableStateOf<Currency?>(null)
        private set

    var convertTo = mutableStateOf<Currency?>(null)
        private set

    var amount = mutableStateOf("0")
        private set

    var isLoadDataPreferences = mutableStateOf(false)
        private set

    init {
        readConvertFromState()
        readConvertToState()
    }

    fun getConversion() {
        viewModelScope.launch {
            conversionRepository.getConversion(
                to = convertTo.value?.code,
                from = convertFrom.value?.code,
                amount = if (amount.value.isEmpty()) 0.0 else amount.value.toDouble()
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { response ->
                            conversionState.value.convert = response
                            conversionState.value = conversionState.value.copy(error = "")
                            val history = History(
                                convertFrom = response.query.from,
                                convertTo = response.query.to,
                                result = response.result,
                                amount = response.query.amount.toDouble(),
                                lastUpdate = response.info.timestamp,
                                createdAt = (System.currentTimeMillis() / 1000).toInt()
                            )
                            historyRepository.insertHistory(history = history)
                        }
                    }
                    is Resource.Error -> {
                        conversionState.value = ConversionState()
                        conversionState.value =
                            result.message?.let { message ->
                                conversionState.value.copy(error = message)
                            }!!
                    }
                    is Resource.Loading -> {
                        conversionState.value =
                            conversionState.value.copy(isLoading = result.isLoading)
                    }
                }
            }
            convertTo.value?.let { persistConvertToState(it) }
            convertFrom.value?.let { persistConvertFromState(it) }
        }
    }

    fun clearResult() {
        conversionState.value = ConversionState()
    }

    fun updateConvertFrom(newConvertFrom: Currency?) {
        convertFrom.value = newConvertFrom
    }

    fun updateConvertTo(newConvertTo: Currency?) {
        convertTo.value = newConvertTo
    }

    fun updateAmount(newAmount: String) {
        amount.value = newAmount
    }

    private fun persistConvertFromState(currency: Currency) {
        viewModelScope.launch {
            conversionRepository.persistConvertFromState(currency)
        }
    }

    private fun persistConvertToState(currency: Currency) {
        viewModelScope.launch {
            conversionRepository.persistConvertToState(currency)
        }
    }

    private fun readConvertFromState() {
        isLoadDataPreferences.value = true
        try {
            viewModelScope.launch {
                conversionRepository.readConvertFromState()
                    .map { currencyCode ->
                        sharedViewModel.getCurrencies().find { it.code == currencyCode }
                    }.collect { currency ->
                        convertFrom.value = currency
                        isLoadDataPreferences.value = false
                    }
            }
        } catch (e: Exception) {
            isLoadDataPreferences.value = false
            e.stackTrace
        }
    }

    private fun readConvertToState() {
        isLoadDataPreferences.value = true
        try {
            viewModelScope.launch {
                conversionRepository.readConvertToState()
                    .map { currencyCode ->
                        sharedViewModel.getCurrencies().find { it.code == currencyCode }
                    }.collect { currency ->
                        convertTo.value = currency
                        isLoadDataPreferences.value = false
                    }
            }
        } catch (e: Exception) {
            isLoadDataPreferences.value = false
            e.stackTrace
        }
    }
}
