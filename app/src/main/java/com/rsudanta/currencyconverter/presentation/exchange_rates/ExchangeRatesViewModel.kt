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

//    fun ratesToString(currency: Currency): String {
//        return when (currency.code) {
//            "AED"->exchangeRatesState.value.exchangeRates?.rates?.AED.toString(),
//            "AFN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ALL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "AMD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ANG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "AOA"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ARS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "AUD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "AWG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "AZN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BAM"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BBD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BDT"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BGN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BHD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BIF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BMD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BND"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BOB"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BRL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BSD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BTC"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BTN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BWP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BYN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BYR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "BZD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CAD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CDF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CHF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CLF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CLP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CNY"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "COP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CRC"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CUC"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CUP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CVE"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "CZK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "DJF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "DKK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "DOP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "DZD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "EGP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ERN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ETB"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "EUR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "FJD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "FKP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GBP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GEL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GGP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GHS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GIP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GMD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GNF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GTQ"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "GYD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "HKD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "HNL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "HRK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "HTG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "HUF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "IDR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ILS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "IMP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "INR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "IQD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "IRR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ISK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "JEP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "JMD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "JOD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "JPY"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KES"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KGS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KHR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KMF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KPW"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KRW"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KWD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KYD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "KZT"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LAK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LBP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LKR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LRD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LSL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LTL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LVL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "LYD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MAD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MDL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MGA"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MKD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MMK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MNT"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MOP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MRO"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MUR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MVR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MWK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MXN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MYR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "MZN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NAD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NGN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NIO"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NOK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NPR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "NZD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "OMR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PAB"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PEN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PGK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PHP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PKR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PLN"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "PYG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "QAR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "RON"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "RSD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "RUB"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "RWF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SAR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SBD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SCR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SDG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SEK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SGD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SHP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SLE"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SLL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SOS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SRD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "STD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SVC"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SYP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "SZL"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "THB"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TJS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TMT"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TND"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TOP"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TRY"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TTD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TWD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "TZS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "UAH"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "UGX"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "USD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "UYU"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "UZS"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "VEF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "VND"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "VUV"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "WST"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XAF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XAG"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XAU"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XCD"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XDR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XOF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "XPF"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "YER"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ZAR"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ZMK"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ZMW"->exchangeRatesState.value.exchangeRates?.rates?..toString(),
//            "ZWL"->exchangeRatesState.value.exchangeRates?.rates?..toString() ,
//            else -> "0"
//        }
//    }

    fun updateBase(newBase: Currency?) {
        base.value = newBase
    }

    fun addTo(newTo: Currency) {
        to.add(newTo)
    }

    fun updateTo(index: Int, newTo: Currency) {
        to[index] = newTo
    }
}
