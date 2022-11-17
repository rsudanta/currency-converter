package com.rsudanta.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesViewModel
import com.rsudanta.currencyconverter.presentation.history.HistoryViewModel
import com.rsudanta.currencyconverter.presentation.main.MainLayout
import com.rsudanta.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val conversionViewModel by viewModels<ConversionViewModel>()
        val historyViewModel by viewModels<HistoryViewModel>()
        val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()
        val sharedViewModel by viewModels<SharedViewModel>()
        setContent {
            CurrencyConverterTheme {
                MainLayout(
                    conversionViewModel = conversionViewModel,
                    historyViewModel = historyViewModel,
                    exchangeRatesViewModel = exchangeRatesViewModel,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}



