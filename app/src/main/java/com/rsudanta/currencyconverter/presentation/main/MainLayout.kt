package com.rsudanta.currencyconverter.presentation.main

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rsudanta.currencyconverter.R
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesViewModel
import com.rsudanta.currencyconverter.presentation.history.HistoryViewModel
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetLayout
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.screenBackground
import com.rsudanta.currencyconverter.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainLayout(
    conversionViewModel: ConversionViewModel,
    historyViewModel: HistoryViewModel,
    sharedViewModel: SharedViewModel,
    exchangeRatesViewModel: ExchangeRatesViewModel
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val conversionError = conversionViewModel.conversionState.value.error
    val exchangeRateError = exchangeRatesViewModel.exchangeRatesState.value.error
    val currentBottomSheet = sharedViewModel.currentBottomSheet.value

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (scaffoldState.bottomSheetState.progress.to == BottomSheetValue.Expanded && scaffoldState.bottomSheetState.progress.fraction > 0)
            0.6f * scaffoldState.bottomSheetState.progress.fraction
        else (1f - scaffoldState.bottomSheetState.progress.fraction) * 0.6f
    )
    val closeSheet: () -> Unit = {
        sharedViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
        sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
        sharedViewModel.updateCurrentSelectedExchangeRatesIndex(newIndex = -1)
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
        }
    }

    val openSheet: (BottomSheetScreen) -> Unit = {
        sharedViewModel.updateCurrentBottomSheet(newCurrentBottomSheet = it)
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(key1 = currentBottomSheet) {
        when (currentBottomSheet) {
            BottomSheetScreen.From ->
                sharedViewModel.updateCurrentBottomSheet(BottomSheetScreen.From)
            BottomSheetScreen.To ->
                sharedViewModel.updateCurrentBottomSheet(BottomSheetScreen.To)
            BottomSheetScreen.BaseExchangeRates ->
                sharedViewModel.updateCurrentBottomSheet(BottomSheetScreen.BaseExchangeRates)
            else ->
                sharedViewModel.updateCurrentBottomSheet(BottomSheetScreen.ToExchangeRates)
        }

    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
        sheetContent = {
            currentBottomSheet?.let {
                BottomSheetLayout(
                    currentScreen = it,
                    onBackPress = {
                        if (scaffoldState.bottomSheetState.isExpanded) {
                            closeSheet()
                        } else {
                            activity?.finish()
                        }
                    },
                    onCloseClick = { closeSheet() },
                    conversionViewModel = conversionViewModel,
                    sharedViewModel = sharedViewModel,
                    exchangeRatesViewModel = exchangeRatesViewModel
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.screenBackground),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Currency\nConverter",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Tabs(
                            pagerState = pagerState,
                            coroutineScope = scope
                        )
                        TabsContent(
                            pagerState = pagerState,
                            conversionViewModel = conversionViewModel,
                            historyViewModel = historyViewModel,
                            sharedViewModel = sharedViewModel,
                            exchangeRatesViewModel = exchangeRatesViewModel,
                            onSelectCurrencyClick = { bottomSheetScreen ->
                                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                openSheet(bottomSheetScreen)
                            },
                            onConvertClick = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                        )
                    }
                }
            }
            if (scaffoldState.bottomSheetState.progress.to == BottomSheetValue.Expanded
                || scaffoldState.bottomSheetState.progress.from == BottomSheetValue.Expanded
            ) {
                Surface(
                    color = Color.Black.copy(backgroundAlpha),
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                if (scaffoldState.bottomSheetState.isExpanded) {
                                    closeSheet()
                                }
                            })
                        }
                ) { }
            }
        }
        DisplaySnackBar(
            scaffoldState = scaffoldState,
            onComplete = {
                if (conversionError.isNotEmpty()) {
                    conversionViewModel.clearResult()
                } else if (exchangeRateError.isNotEmpty()) {
                    exchangeRatesViewModel.clearResult()
                }
            },
            errorMessage = conversionError.ifEmpty { exchangeRateError }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplaySnackBar(
    scaffoldState: BottomSheetScaffoldState,
    onComplete: () -> Unit,
    errorMessage: String,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage,
                    actionLabel = "OK",
                    duration = SnackbarDuration.Long
                )
            }
            onComplete()
        }
    }
}