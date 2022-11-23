package com.rsudanta.currencyconverter.presentation.main.bottom_sheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesViewModel

@Composable
fun BottomSheetLayout(
    conversionViewModel: ConversionViewModel,
    sharedViewModel: SharedViewModel,
    exchangeRatesViewModel: ExchangeRatesViewModel,
    currentScreen: BottomSheetScreen,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp * 0.75f

    when (currentScreen) {
        BottomSheetScreen.From
        -> ConverterCurrencyListBottomSheet(
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick,
            conversionViewModel = conversionViewModel,
            sharedViewModel = sharedViewModel,
            title = "From"
        )
        BottomSheetScreen.To
        -> ConverterCurrencyListBottomSheet(
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick,
            conversionViewModel = conversionViewModel,
            sharedViewModel = sharedViewModel,
            title = "To"
        )
        BottomSheetScreen.BaseExchangeRates
        -> ExchangeRatesCurrencyListBottomSheet(
            sharedViewModel = sharedViewModel,
            exchangeRatesViewModel = exchangeRatesViewModel,
            title = "Base",
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick
        )
        BottomSheetScreen.ToExchangeRates
        -> ExchangeRatesCurrencyListBottomSheet(
            sharedViewModel = sharedViewModel,
            exchangeRatesViewModel = exchangeRatesViewModel,
            title = "To",
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick
        )
    }
}