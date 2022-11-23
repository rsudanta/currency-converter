package com.rsudanta.currencyconverter.presentation.exchange_rates

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.domain.repository.ExchangeRatesRepository
import com.rsudanta.currencyconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(private val repository: ExchangeRatesRepository) :
    ViewModel() {

    var exchangeRatesState = mutableStateOf(ExchangeRatesState())
        private set

    var base = mutableStateOf<Currency?>(null)
        private set

    var to = mutableStateListOf<Currency?>()
        private set

    fun getExchangeRates() {
        if (base.value != null && to.size > 0) {
            val symbols: String = to.joinToString(separator = ",") { it!!.code }
            val base: String = base.value?.code!!
            viewModelScope.launch {
                repository.getExchangeRates(base = base, symbols = symbols)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let { response ->
                                    exchangeRatesState.value.exchangeRates = response
                                    Log.d("???", exchangeRatesState.value.toString())
                                }
                            }
                            is Resource.Error -> {
                                exchangeRatesState.value = ExchangeRatesState()
                                exchangeRatesState.value =
                                    result.message?.let { message ->
                                        exchangeRatesState.value.copy(error = message)
                                    }!!
                            }
                            is Resource.Loading -> {
                                exchangeRatesState.value =
                                    exchangeRatesState.value.copy(isLoading = result.isLoading)
                            }
                        }
                    }
            }
        }
    }

    fun updateBase(newBase: Currency?) {
        base.value = newBase
    }

    fun addToCurrencyList(newTo: Currency) {
        to.add(newTo)
    }

    fun removeFromToCurrencyList(index: Int) {
        to.removeAt(index)
    }

    fun updateToCurrency(index: Int, newTo: Currency) {
        to[index] = newTo
    }

    fun clearResult() {
        exchangeRatesState.value = ExchangeRatesState()
    }
}
