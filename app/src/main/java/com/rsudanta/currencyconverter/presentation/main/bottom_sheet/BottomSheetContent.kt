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
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrencyListBottomSheet(
    viewModel: ConversionViewModel,
    title: String,
    screenHeight: Dp,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    val currencies = viewModel.getCurrencies()

    val from = viewModel.convertFrom.value
    val to = viewModel.convertTo.value
    val searchAppBarState = viewModel.searchAppBarState.value
    val searchCurrencyText = viewModel.searchCurrencyText.value

    val scope = rememberCoroutineScope()
    BackHandler {
        if (searchCurrencyText.isNotEmpty()) {
            viewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
        } else if (searchAppBarState != SearchAppBarState.CLOSED) {
            viewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
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
                        viewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
                    } else if (searchAppBarState != SearchAppBarState.CLOSED) {
                        viewModel.updateSearchAppBarState(newSearchAppBarState = SearchAppBarState.CLOSED)
                    } else {
                        onCloseClick()
                    }
                },
                onSearchClick = { newSearchAppBarState ->
                    viewModel.updateSearchAppBarState(newSearchAppBarState = newSearchAppBarState)
                },
                searchCurrencyText = searchCurrencyText,
                onSearchCurrencyTextChange = { searchCurrency ->
                    viewModel.updateSearchCurrencyText(newSearchCurrencyText = searchCurrency)
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
                                            viewModel.updateConvertFrom(newConvertFrom = null)
                                            delay(300L)
                                        }
                                        viewModel.updateConvertFrom(currency)
                                    }
                                } else {
                                    scope.launch {
                                        if (to != null) {
                                            viewModel.updateConvertTo(newConvertTo = null)
                                            delay(300L)
                                        }
                                        viewModel.updateConvertTo(currency)
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




