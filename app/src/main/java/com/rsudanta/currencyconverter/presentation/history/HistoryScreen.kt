package com.rsudanta.currencyconverter.presentation.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsudanta.currencyconverter.presentation.common.DisplayAlertDialog
import com.rsudanta.currencyconverter.ui.theme.historyBackground
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.screenBackground
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.util.formatWithComma
import com.rsudanta.currencyconverter.util.timestampToDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val historyState = viewModel.historyState.value
    var openDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var itemAppeared by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getHistories()
        delay(300L)
        itemAppeared = true
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        DisplayAlertDialog(
            title = "Remove all histories?",
            message = "Are you sure you want to remove All Histories?",
            openDialog = openDialog,
            closeDialog = { openDialog = false },
            onYesClicked = {
                itemAppeared = false
                scope.launch {
                    delay(400)
                    viewModel.deleteAllHistories()
                }
            }
        )
        LazyColumn {
            itemsIndexed(historyState.histories.distinctBy { it.createdAt.timestampToDate() }) { index, history ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if ((System.currentTimeMillis() / 1000).toInt()
                                .timestampToDate() == history.createdAt.timestampToDate()
                        ) "Today" else "${history.createdAt.timestampToDate()}",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    if (index == 0) {
                        Text(
                            modifier = Modifier.clickable { openDialog = true },
                            text = "Delete All",
                            style = TextStyle(
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                historyState.histories.filter {
                    history.createdAt.timestampToDate() == it.createdAt.timestampToDate()
                }.forEachIndexed { index, history ->
                    AnimatedVisibility(
                        visible = itemAppeared,
                        enter = expandVertically(animationSpec = tween(durationMillis = 300)),
                        exit = shrinkVertically(animationSpec = tween(durationMillis = 300))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (index % 2 == 0) MaterialTheme.colors.screenBackground
                                    else MaterialTheme.colors.historyBackground
                                )
                                .padding(
                                    start = 20.dp,
                                    end = 4.dp,
                                    bottom = 8.dp,
                                    top = 8.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${
                                    history.amount.formatWithComma()
                                } ${history.convertFrom} =  ${
                                    history.result.formatWithComma()
                                } ${history.convertTo}",
                                style = TextStyle(
                                    fontFamily = poppins,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colors.textPrimary
                                )
                            )
                            IconButton(onClick = {
                                itemAppeared = false
                                scope.launch {
                                    delay(400)
                                    viewModel.deleteHistory(history = history)
                                    itemAppeared = true
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Delete icon",
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        if (historyState.histories.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No conversion history",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.textPrimary,
                    )
                )
            }
        }
        if (historyState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}