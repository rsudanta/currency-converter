package com.rsudanta.currencyconverter.presentation.main

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet.BottomSheetLayout
import com.rsudanta.currencyconverter.presentation.conversion.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.ui.theme.poppins
import com.rsudanta.currencyconverter.ui.theme.screenBackground
import com.rsudanta.currencyconverter.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainLayout(viewModel: ConversionViewModel) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val conversionError = viewModel.conversionState.value.error
    val currentBottomSheet = viewModel.currentBottomSheet.value

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (scaffoldState.bottomSheetState.progress.to == BottomSheetValue.Expanded && scaffoldState.bottomSheetState.progress.fraction > 0)
            0.6f * scaffoldState.bottomSheetState.progress.fraction
        else (1f - scaffoldState.bottomSheetState.progress.fraction) * 0.6f
    )
    val closeSheet: () -> Unit = {
        viewModel.updateSearchAppBarState(SearchAppBarState.CLOSED)
        viewModel.updateSearchCurrencyText(newSearchCurrencyText = "")
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
        }
    }

    val openSheet: (BottomSheetScreen) -> Unit = {
        viewModel.updateCurrentBottomSheet(it)
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(key1 = currentBottomSheet) {
        if (currentBottomSheet == BottomSheetScreen.From) {
            viewModel.updateCurrentBottomSheet(BottomSheetScreen.From)
        } else {
            viewModel.updateCurrentBottomSheet(BottomSheetScreen.To)
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
                    viewModel = viewModel
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
                            viewModel = viewModel,
                            onSelectCurrencyClick = { bottomSheetScreen ->
                                openSheet(bottomSheetScreen)
                            },
                            onConvertClick = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() }
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
            onComplete = { viewModel.clearResult() },
            errorMessage = conversionError
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