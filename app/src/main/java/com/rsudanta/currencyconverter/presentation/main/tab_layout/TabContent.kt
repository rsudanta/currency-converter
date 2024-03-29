package com.rsudanta.currencyconverter.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.presentation.conversion.ConversionScreen
import com.rsudanta.currencyconverter.presentation.conversion.ConversionViewModel
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesScreen
import com.rsudanta.currencyconverter.presentation.exchange_rates.ExchangeRatesViewModel
import com.rsudanta.currencyconverter.presentation.history.HistoryScreen
import com.rsudanta.currencyconverter.presentation.history.HistoryViewModel
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.presentation.main.tab_layout.Pages
import com.rsudanta.currencyconverter.ui.theme.poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState, coroutineScope: CoroutineScope) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .width(0.dp)
                    .height(0.dp)
            )
        },
        divider = {}
    ) {
        val pages = Pages.values()
        pages.forEachIndexed { index, page ->
            Tab(
                modifier = Modifier
                    .background(
                        color = if (pagerState.currentPage == index)
                            MaterialTheme.colors.primary
                        else Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ),
                text = {
                    Text(
                        text = page.title,
                        color = if (pagerState.currentPage == index) Color.White else MaterialTheme.colors.primary,
                        fontFamily = poppins,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}


@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    conversionViewModel: ConversionViewModel,
    historyViewModel: HistoryViewModel,
    sharedViewModel: SharedViewModel,
    exchangeRatesViewModel: ExchangeRatesViewModel,
    onSelectCurrencyClick: (BottomSheetScreen) -> Unit,
    onConvertClick: () -> Unit
) {
    HorizontalPager(state = pagerState, count = Pages.values().size) { page ->
        when (page) {
            0 -> ConversionScreen(
                viewModel = conversionViewModel,
                onSelectCurrencyClick = onSelectCurrencyClick,
                onConvertClick = onConvertClick
            )
            1 -> ExchangeRatesScreen(
                exchangeRatesViewModel = exchangeRatesViewModel,
                onSelectCurrencyClick = onSelectCurrencyClick,
                sharedViewModel = sharedViewModel
            )
            2 -> HistoryScreen(viewModel = historyViewModel)
        }
    }
}