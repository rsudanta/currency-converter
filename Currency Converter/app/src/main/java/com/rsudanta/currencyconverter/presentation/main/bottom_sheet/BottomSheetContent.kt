package com.rsudanta.currencyconverter.presentation.main.bottom_sheet

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesViewModel
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConverterCurrencyListBottomSheet(
    conversionViewModel: ConversionViewModel,
    sharedViewModel: SharedViewModel,
    title: String,
    screenHeight: Dp,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    val currencies = sharedViewModel.getCurrencies()

    val from = conversionViewModel.convertFrom.value
    val to = conversionViewModel.convertTo.value
    val searchAppBarState = sharedViewModel.searchAppBarState.value
    val searchCurrencyText = sharedViewModel.searchCurrencyText.value

    val scope = rememberCoroutineScope()
    BackHandler {
        if (searchCurrencyText.isNotEmpty()) {
            sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
        } else if (searchAppBarState != SearchAppBarState.CLOSED) {
            sharedViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
        } else {
            onBackPress()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight),
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                title = title,
                onCloseClick = {
                    if (searchCurrencyText.isNotEmpty()) {
                        sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
                    } else if (searchAppBarState != SearchAppBarState.CLOSED) {
                        sharedViewModel.updateSearchAppBarState(newSearchAppBarState = SearchAppBarState.CLOSED)
                    } else {
                        onCloseClick()
                    }
                },
                onSearchClick = { newSearchAppBarState ->
                    sharedViewModel.updateSearchAppBarState(newSearchAppBarState = newSearchAppBarState)
                },
                searchCurrencyText = searchCurrencyText,
                onSearchCurrencyTextChange = { searchCurrency ->
                    sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = searchCurrency)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (title == "From") {
                AnimatedVisibility(visible = from?.name != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = from?.name ?: "",
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check Icon",
                            tint = Color.White
                        )
                    }

                }

            } else {
                AnimatedVisibility(visible = to?.name != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = to?.name ?: "",
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check Icon",
                            tint = Color.White
                        )
                    }
                }

            }
            if (currencies.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = "No results available",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.textPrimary,
                        )
                    )
                }
            }
            LazyColumn {
                items(items = currencies
                    .filter { it != from && it != to }
                    .sortedBy { it.name }) { currency ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (title == "From") {
                                    scope.launch {
                                        if (from != null) {
                                            conversionViewModel.updateConvertFrom(newConvertFrom = null)
                                            delay(300L)
                                        }
                                        conversionViewModel.updateConvertFrom(currency)
                                    }
                                } else {
                                    scope.launch {
                                        if (to != null) {
                                            conversionViewModel.updateConvertTo(newConvertTo = null)
                                            delay(300L)
                                        }
                                        conversionViewModel.updateConvertTo(currency)
                                    }
                                }
                            }
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                    ) {
                        Text(
                            text = currency.name,
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.textPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExchangeRatesCurrencyListBottomSheet(
    sharedViewModel: SharedViewModel,
    exchangeRatesViewModel: ExchangeRatesViewModel,
    title: String,
    screenHeight: Dp,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    val currencies = sharedViewModel.getCurrencies()

    val base = exchangeRatesViewModel.base.value
    val to = exchangeRatesViewModel.to
    val searchAppBarState = sharedViewModel.searchAppBarState.value
    val searchCurrencyText = sharedViewModel.searchCurrencyText.value
    val currentSelectedIndex = sharedViewModel.currentSelectedExchangeRatesIndex.value

    val scope = rememberCoroutineScope()
    BackHandler {
        if (searchCurrencyText.isNotEmpty()) {
            sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
        } else if (searchAppBarState != SearchAppBarState.CLOSED) {
            sharedViewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
        } else {
            onBackPress()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight),
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                title = title,
                onCloseClick = {
                    if (searchCurrencyText.isNotEmpty()) {
                        sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
                    } else if (searchAppBarState != SearchAppBarState.CLOSED) {
                        sharedViewModel.updateSearchAppBarState(newSearchAppBarState = SearchAppBarState.CLOSED)
                    } else {
                        onCloseClick()
                    }
                },
                onSearchClick = { newSearchAppBarState ->
                    sharedViewModel.updateSearchAppBarState(newSearchAppBarState = newSearchAppBarState)
                },
                searchCurrencyText = searchCurrencyText,
                onSearchCurrencyTextChange = { searchCurrency ->
                    sharedViewModel.updateSearchCurrencyText(newSearchCurrencyText = searchCurrency)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (title == "Base") {
                AnimatedVisibility(visible = base?.name != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = base?.name ?: "",
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check Icon",
                            tint = Color.White
                        )
                    }
                }
            } else {
                AnimatedVisibility(visible = currentSelectedIndex != -1) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (currentSelectedIndex != -1) {
                            Text(
                                text = to[currentSelectedIndex]?.name ?: "",
                                style = TextStyle(
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Check Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            if (currencies.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = "No results available",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.textPrimary,
                        )
                    )
                }
            }
            LazyColumn {
                items(items = currencies
                    .filter { if (title == "Base") it != base else !to.contains(it) }
                    .sortedBy { it.name }) { currency ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (title == "Base") {
                                    scope.launch {
                                        if (base != null) {
                                            exchangeRatesViewModel.updateBase(newBase = null)
                                            delay(300L)
                                        }
                                        exchangeRatesViewModel.updateBase(currency)
                                        exchangeRatesViewModel.getExchangeRates()
                                    }
                                } else {
                                    if (currentSelectedIndex == -1) {
                                        exchangeRatesViewModel.addToCurrencyList(currency)
                                    } else {
                                        exchangeRatesViewModel.updateToCurrency(
                                            currentSelectedIndex,
                                            currency
                                        )
                                    }
                                    exchangeRatesViewModel.getExchangeRates()
                                    onCloseClick()
                                }
                            }
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                    ) {
                        Text(
                            text = currency.name,
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.textPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}




