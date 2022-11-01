package com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet

sealed class BottomSheetScreen() {
    object From : BottomSheetScreen()
    object To : BottomSheetScreen()
}
