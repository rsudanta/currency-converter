package com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel

@Composable
fun BottomSheetLayout(
    viewModel: ConversionViewModel,
    currentScreen: BottomSheetScreen,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp * 0.75f

    when (currentScreen) {
        BottomSheetScreen.From
        -> CurrencyBottomSheet(
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick,
            viewModel = viewModel,
            title = "From"
        )
        BottomSheetScreen.To
        -> CurrencyBottomSheet(
            screenHeight = screenHeight,
            onBackPress = { onBackPress() },
            onCloseClick = onCloseClick,
            viewModel = viewModel,
            title = "To"
        )
    }
}