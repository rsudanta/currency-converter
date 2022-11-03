package com.rsudanta.currencyconverter.presentation.conversion

import DotsLoading
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.rsudanta.currencyconverter.R
import com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textFieldBackground
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.ui.theme.textSecondary
import com.rsudanta.currencyconverter.util.NumberCommaTransformation
import com.rsudanta.currencyconverter.util.formatWithComma
import com.rsudanta.currencyconverter.util.timestampToDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConversionScreen(
    viewModel: ConversionViewModel,
    onSelectCurrencyClick: (BottomSheetScreen) -> Unit,
    onConvertClick: () -> Unit
) {
    val convertFrom = viewModel.convertFrom.value
    val convertTo = viewModel.convertTo.value
    val amount = viewModel.amount.value
    val conversionState = viewModel.conversionState.value
    val isLoadDataPreferences = viewModel.isLoadDataPreferences.value

    val scrollState = rememberScrollState()
    var isSwapClick by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (isSwapClick) 90f else 270f,
        animationSpec = tween(
            durationMillis = 200
        )
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = conversionState.isLoading || conversionState.convert?.result != null,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            ConversionResultBox(
                conversionState = conversionState,
                onClearClick = {
                    viewModel.clearResult()
                })
        }
        if (conversionState.isLoading || conversionState.convert?.result != null) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        CurrencyListButton(
            label = "From",
            selectedCurrency = if (isLoadDataPreferences) "..." else convertFrom?.name,
            onSelectCurrencyClick = {
                onSelectCurrencyClick(BottomSheetScreen.From)
            })
        IconButton(onClick = {
            if (convertTo != null || convertFrom != null) {
                isSwapClick = !isSwapClick
                viewModel.updateConvertFrom(newConvertFrom = convertTo)
                viewModel.updateConvertTo(newConvertTo = convertFrom)
            }
        }) {
            Icon(
                modifier = Modifier.rotate(degrees = angle),
                painter = painterResource(id = R.drawable.ic_swap),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        CurrencyListButton(
            label = "To",
            selectedCurrency = if (isLoadDataPreferences) "..." else convertTo?.name,
            onSelectCurrencyClick = {
                onSelectCurrencyClick(BottomSheetScreen.To)
            })
        Spacer(modifier = Modifier.height(24.dp))
        AmountTextField(amount = amount, onAmountChange = {
            viewModel.updateAmount(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onConvertClick()
                viewModel.getConversion()
                viewModel.updateAmount(newAmount = "0")
                scope.launch {
                    scrollState.scrollTo(value = 0)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "Convert", fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
        }
    }
}


@Composable
fun AmountTextField(amount: String, onAmountChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Amount",
            modifier = Modifier.padding(end = 8.dp),
            style = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colors.textPrimary
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = amount,
            onValueChange = { newText: String ->
                if (newText.length <= Long.MAX_VALUE.toString().length && newText.isDigitsOnly()) {
                    onAmountChange(newText)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            visualTransformation = NumberCommaTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.textFieldBackground,
                textColor = MaterialTheme.colors.textPrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(4.dp)
        )
    }

}

@Composable
fun CurrencyListButton(
    label: String,
    selectedCurrency: String?,
    onSelectCurrencyClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colors.textPrimary
            )
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelectCurrencyClick() },
            backgroundColor = MaterialTheme.colors.textFieldBackground
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = selectedCurrency ?: "Select Currency",
                textAlign = TextAlign.Center,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = if (selectedCurrency.isNullOrEmpty()) MaterialTheme.colors.textSecondary else MaterialTheme.colors.textPrimary
            )
        }
    }
}

@Composable
fun ConversionResultBox(conversionState: ConversionState, onClearClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (conversionState.isLoading) {
            DotsLoading()
        } else {
            conversionState.convert?.result?.let { result ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Result",
                            style = TextStyle(
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.textPrimary
                            )
                        )
                        Text(
                            text = "Clear",
                            modifier = Modifier.clickable {
                                onClearClick()
                            },
                            style = TextStyle(
                                fontFamily = poppins,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                    Text(
                        text = "${
                            conversionState.convert?.query?.amount?.toLong().formatWithComma()
                        } " +
                                "${conversionState.convert?.query?.from} = " +
                                "${result.toLong().formatWithComma()} " +
                                "${conversionState.convert?.query?.to}",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Last update ${conversionState.convert?.info?.timestamp?.timestampToDate()}",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    )
                }
            }
        }
    }
}
