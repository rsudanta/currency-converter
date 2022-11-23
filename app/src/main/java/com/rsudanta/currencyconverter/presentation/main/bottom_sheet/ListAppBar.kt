package com.rsudanta.currencyconverter.presentation.main.bottom_sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rsudanta.currencyconverter.ui.theme.textPrimary
import com.rsudanta.currencyconverter.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    title: String,
    onCloseClick: () -> Unit,
    onSearchClick: (SearchAppBarState) -> Unit,
    searchCurrencyText: String,
    onSearchCurrencyTextChange: (String) -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                title = title,
                onCloseClick = onCloseClick,
                onSearchClick = onSearchClick
            )
        }
        else -> {
            SearchAppBar(
                searchCurrencyText = searchCurrencyText,
                onSearchCurrencyTextChange = onSearchCurrencyTextChange,
                onCloseClick = onCloseClick,
            )
        }
    }
}

@Composable
fun SearchAppBar(
    searchCurrencyText: String,
    onSearchCurrencyTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
) {
    val focusRequester = remember {
        FocusRequester()
    }

    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
            value = searchCurrencyText,
            onValueChange = {
                onSearchCurrencyTextChange(it)
            },
            placeholder = {
                Text(
                    text = "Enter currency",
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search disabled icon",
                )
            },
            trailingIcon = {
                CloseAction(onCloseClick = onCloseClick)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    localFocusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.textPrimary,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
fun DefaultListAppBar(
    title: String,
    onCloseClick: () -> Unit,
    onSearchClick: (SearchAppBarState) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            ListAppBarActions(
                onCloseClick = onCloseClick,
                onSearchClick = {
                    onSearchClick(SearchAppBarState.OPENED)
                })
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun ListAppBarActions(onSearchClick: () -> Unit, onCloseClick: () -> Unit) {
    SearchAction(onSearchClick = onSearchClick)
    CloseAction(onCloseClick = onCloseClick)
}

@Composable
fun SearchAction(onSearchClick: () -> Unit) {
    IconButton(onClick = { onSearchClick() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search icon",
            tint = Color.White
        )
    }
}

@Composable
fun CloseAction(onCloseClick: () -> Unit) {
    IconButton(onClick = { onCloseClick() }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close icon",
            tint = Color.White
        )
    }
}

