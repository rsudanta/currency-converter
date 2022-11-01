package com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
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


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrencyBottomSheet(
    viewModel: ConversionViewModel,
    title: String,
    screenHeight: Dp,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    val currencies = viewModel.getCurrencies()
    val from = viewModel.convertFrom.value
    val to = viewModel.convertTo.value
    BackHandler {
        onBackPress()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight),
        topBar = {
            ListAppBar(
                searchAppBarState = SearchAppBarState.CLOSED,
                title = title,
                onCloseClick = onCloseClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (title == "From") {
                from?.let {
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
                            text = from.name,
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
                to?.let {
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
                            text = to.name,
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
            LazyColumn {
                if (title == "From") {
                    items(items = currencies.filter { it != from && it != to }) { currency ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.updateConvertFrom(currency) }
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
                } else {
                    items(items = currencies.filter { it != from && it != to }) { currency ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.updateConvertTo(currency) }
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
}


