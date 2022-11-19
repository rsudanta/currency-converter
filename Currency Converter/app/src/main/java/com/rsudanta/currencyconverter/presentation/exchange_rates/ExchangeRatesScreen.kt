package com.rsudanta.currencyconverter.presentation.exchange_rates

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsudanta.currencyconverter.R
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.common.DisplayAlertDialog
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textFieldBackground
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.ui.theme.textSecondary
import com.rsudanta.currencyconverter.util.formatWithComma
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExchangeRatesScreen(
    exchangeRatesViewModel: ExchangeRatesViewModel,
    sharedViewModel: SharedViewModel,
    onSelectCurrencyClick: (BottomSheetScreen) -> Unit,
) {
    val base = exchangeRatesViewModel.base.value
    val to = exchangeRatesViewModel.to
    val result = exchangeRatesViewModel.exchangeRatesState.value
    val currentIndexSelected = sharedViewModel.currentSelectedExchangeRatesIndex.value
    var openDialog by remember { mutableStateOf(false) }

    Surface {
        if (currentIndexSelected != -1) {
            DisplayAlertDialog(
                title = "Remove ${to[currentIndexSelected]?.name}",
                message = "Are you sure you want to remove ${to[currentIndexSelected]?.name}?",
                openDialog = openDialog,
                closeDialog = { openDialog = false },
                onYesClicked = {

                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectExchangeCurrencyButton(
                label = "Base",
                selectedCurrency = base,
                onSelectCurrencyClick = { onSelectCurrencyClick(BottomSheetScreen.BaseExchangeRates) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_convert),
                contentDescription = "Convert Icon",
                tint = MaterialTheme.colors.textPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                itemsIndexed(to) { index, item ->
                    SelectExchangeCurrencyButton(
                        selectedCurrency = item,
                        onSelectCurrencyClick = {
                            sharedViewModel.updateCurrentSelectedExchangeRatesIndex(index)
                            onSelectCurrencyClick(BottomSheetScreen.ToExchangeRates)
                        },
                        result = if (result.isLoading) "..." else
                            result.exchangeRates?.rates?.get(index)?.rate.formatWithComma(),
                        onLongPress = {
                            sharedViewModel.updateCurrentSelectedExchangeRatesIndex(index)
                            openDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            SelectExchangeCurrencyButton(
                selectedCurrency = null,
                onSelectCurrencyClick = {
                    sharedViewModel.updateCurrentSelectedExchangeRatesIndex(newIndex = -1)
                    onSelectCurrencyClick(BottomSheetScreen.ToExchangeRates)
                }
            )
        }
    }

}

@Composable
fun SelectExchangeCurrencyButton(
    label: String? = null,
    selectedCurrency: Currency?,
    onSelectCurrencyClick: () -> Unit,
    result: String = "",
    onLongPress: () -> Unit = {}
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
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() },
                        onTap = { onSelectCurrencyClick() }
                    )
                },
            backgroundColor = MaterialTheme.colors.textFieldBackground
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = selectedCurrency?.name ?: "Select Currency",
                    textAlign = if (selectedCurrency?.name.isNullOrEmpty()) TextAlign.Center else TextAlign.Start,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = if (selectedCurrency?.name.isNullOrEmpty()) MaterialTheme.colors.textSecondary else MaterialTheme.colors.textPrimary
                )
                if (!selectedCurrency?.name.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = if (label == "Base") "1 ${selectedCurrency!!.code}" else "$result ${selectedCurrency!!.code}",
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