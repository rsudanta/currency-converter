package com.rsudanta.currencyconverter.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.ui.theme.textSecondary

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.textPrimary
                    )
                )
            },
            text = {
                Text(
                    text = message,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.textSecondary
                    )
                )
            },
            confirmButton = {
                Button(modifier = Modifier.padding(bottom = 12.dp, end = 12.dp),
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    }) {
                    Text(
                        text = "Yes", style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    closeDialog()
                }) {
                    Text(
                        text = "No", style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.textSecondary
                        )
                    )
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }
}