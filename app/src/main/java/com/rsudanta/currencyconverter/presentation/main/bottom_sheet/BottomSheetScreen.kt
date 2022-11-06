package com.rsudanta.currencyconverter.presentation.main.bottom_sheet

sealed class BottomSheetScreen() {
    object From : BottomSheetScreen()
    object To : BottomSheetScreen()
}
