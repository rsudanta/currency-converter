package com.rsudanta.currencyconverter.presentation.exchange_rates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textFieldBackground
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.ui.theme.textSecondary

@Composable
fun ExchangeRatesScreen(exchangeRatesViewModel: ExchangeRatesViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        SelectExchangeCurrencyButton(
            label = "Base",
            selectedCurrency = "idr",
            onSelectCurrencyClick = {})
    }
}

@Composable
fun SelectExchangeCurrencyButton(
    label: String? = null,
    selectedCurrency: String?,
    onSelectCurrencyClick: () -> Unit
) {
    Column {
        label?.let {
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.textPrimary
                )
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelectCurrencyClick() },
            backgroundColor = MaterialTheme.colors.textFieldBackground
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = selectedCurrency ?: "Select Currency",
                    textAlign = if (selectedCurrency.isNullOrEmpty()) TextAlign.Center else TextAlign.Start,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = if (selectedCurrency.isNullOrEmpty()) MaterialTheme.colors.textSecondary else MaterialTheme.colors.textPrimary
                )
                if (!selectedCurrency.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = "1",
                        textAlign = TextAlign.End,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}