package com.rsudanta.currencyconverter.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.presentation.main.bottom_sheet.BottomSheetScreen
import com.rsudanta.currencyconverter.util.SearchAppBarState

class SharedViewModel : ViewModel() {

    var searchAppBarState = mutableStateOf(SearchAppBarState.CLOSED)
        private set

    var searchCurrencyText = mutableStateOf("")
        private set

    var currentBottomSheet: MutableState<BottomSheetScreen?> = mutableStateOf(null)
        private set

    var currentSelectedExchangeRatesIndex: MutableState<Int> = mutableStateOf(-1)
        private set

    fun updateCurrentBottomSheet(newCurrentBottomSheet: BottomSheetScreen) {
        currentBottomSheet.value = newCurrentBottomSheet
    }

    fun updateSearchAppBarState(newSearchAppBarState: SearchAppBarState) {
        searchAppBarState.value = newSearchAppBarState
    }

    fun updateSearchCurrencyText(newSearchCurrencyText: String) {
        searchCurrencyText.value = newSearchCurrencyText
    }

    fun updateCurrentSelectedExchangeRatesIndex(newIndex: Int) {
        currentSelectedExchangeRatesIndex.value = newIndex
    }

    fun getCurrencies() = listOf(
        Currency(code = "AED", name = "United Arab Emirates Dirham"),
        Currency(code = "AFN", name = "Afghan Afghani"),
        Currency(code = "ALL", name = "Albanian Lek"),
        Currency(code = "AMD", name = "Armenian Dram"),
        Currency(code = "ANG", name = "Netherlands Antillean Guilder"),
        Currency(code = "AOA", name = "Angolan Kwanza"),
        Currency(code = "ARS", name = "Argentine Peso"),
        Currency(code = "AUD", name = "Australian Dollar"),
        Currency(code = "AWG", name = "Aruban Florin"),
        Currency(code = "AZN", name = "Azerbaijani Manat"),
        Currency(code = "BAM", name = "Bosnia-Herzegovina Convertible Mark"),
        Currency(code = "BBD", name = "Barbadian Dollar"),
        Currency(code = "BDT", name = "Bangladeshi Taka"),
        Currency(code = "BGN", name = "Bulgarian Lev"),
        Currency(code = "BHD", name = "Bahraini Dinar"),
        Currency(code = "BIF", name = "Burundian Franc"),
        Currency(code = "BMD", name = "Bermudan Dollar"),
        Currency(code = "BND", name = "Brunei Dollar"),
        Currency(code = "BOB", name = "Bolivian Boliviano"),
        Currency(code = "BRL", name = "Brazilian Real"),
        Currency(code = "BSD", name = "Bahamian Dollar"),
        Currency(code = "BTC", name = "Bitcoin"),
        Currency(code = "BTN", name = "Bhutanese Ngultrum"),
        Currency(code = "BWP", name = "Botswanan Pula"),
        Currency(code = "BYN", name = "New Belarusian Ruble"),
        Currency(code = "BYR", name = "Belarusian Ruble"),
        Currency(code = "BZD", name = "Belize Dollar"),
        Currency(code = "CAD", name = "Canadian Dollar"),
        Currency(code = "CDF", name = "Congolese Franc"),
        Currency(code = "CHF", name = "Swiss Franc"),
        Currency(code = "CLF", name = "Chilean Unit of Account (UF)"),
        Currency(code = "CLP", name = "Chilean Peso"),
        Currency(code = "CNY", name = "Chinese Yuan"),
        Currency(code = "COP", name = "Colombian Peso"),
        Currency(code = "CRC", name = "Costa Rican Col\u00f3n"),
        Currency(code = "CUC", name = "Cuban Convertible Peso"),
        Currency(code = "CUP", name = "Cuban Peso"),
        Currency(code = "CVE", name = "Cape Verdean Escudo"),
        Currency(code = "CZK", name = "Czech Republic Koruna"),
        Currency(code = "DJF", name = "Djiboutian Franc"),
        Currency(code = "DKK", name = "Danish Krone"),
        Currency(code = "DOP", name = "Dominican Peso"),
        Currency(code = "DZD", name = "Algerian Dinar"),
        Currency(code = "EGP", name = "Egyptian Pound"),
        Currency(code = "ERN", name = "Eritrean Nakfa"),
        Currency(code = "ETB", name = "Ethiopian Birr"),
        Currency(code = "EUR", name = "Euro"),
        Currency(code = "FJD", name = "Fijian Dollar"),
        Currency(code = "FKP", name = "Falkland Islands Pound"),
        Currency(code = "GBP", name = "British Pound Sterling"),
        Currency(code = "GEL", name = "Georgian Lari"),
        Currency(code = "GGP", name = "Guernsey Pound"),
        Currency(code = "GHS", name = "Ghanaian Cedi"),
        Currency(code = "GIP", name = "Gibraltar Pound"),
        Currency(code = "GMD", name = "Gambian Dalasi"),
        Currency(code = "GNF", name = "Guinean Franc"),
        Currency(code = "GTQ", name = "Guatemalan Quetzal"),
        Currency(code = "GYD", name = "Guyanaese Dollar"),
        Currency(code = "HKD", name = "Hong Kong Dollar"),
        Currency(code = "HNL", name = "Honduran Lempira"),
        Currency(code = "HRK", name = "Croatian Kuna"),
        Currency(code = "HTG", name = "Haitian Gourde"),
        Currency(code = "HUF", name = "Hungarian Forint"),
        Currency(code = "IDR", name = "Indonesian Rupiah"),
        Currency(code = "ILS", name = "Israeli New Sheqel"),
        Currency(code = "IMP", name = "Manx pound"),
        Currency(code = "INR", name = "Indian Rupee"),
        Currency(code = "IQD", name = "Iraqi Dinar"),
        Currency(code = "IRR", name = "Iranian Rial"),
        Currency(code = "ISK", name = "Icelandic Kr\u00f3na"),
        Currency(code = "JEP", name = "Jersey Pound"),
        Currency(code = "JMD", name = "Jamaican Dollar"),
        Currency(code = "JOD", name = "Jordanian Dinar"),
        Currency(code = "JPY", name = "Japanese Yen"),
        Currency(code = "KES", name = "Kenyan Shilling"),
        Currency(code = "KGS", name = "Kyrgystani Som"),
        Currency(code = "KHR", name = "Cambodian Riel"),
        Currency(code = "KMF", name = "Comorian Franc"),
        Currency(code = "KPW", name = "North Korean Won"),
        Currency(code = "KRW", name = "South Korean Won"),
        Currency(code = "KWD", name = "Kuwaiti Dinar"),
        Currency(code = "KYD", name = "Cayman Islands Dollar"),
        Currency(code = "KZT", name = "Kazakhstani Tenge"),
        Currency(code = "LAK", name = "Laotian Kip"),
        Currency(code = "LBP", name = "Lebanese Pound"),
        Currency(code = "LKR", name = "Sri Lankan Rupee"),
        Currency(code = "LRD", name = "Liberian Dollar"),
        Currency(code = "LSL", name = "Lesotho Loti"),
        Currency(code = "LTL", name = "Lithuanian Litas"),
        Currency(code = "LVL", name = "Latvian Lats"),
        Currency(code = "LYD", name = "Libyan Dinar"),
        Currency(code = "MAD", name = "Moroccan Dirham"),
        Currency(code = "MDL", name = "Moldovan Leu"),
        Currency(code = "MGA", name = "Malagasy Ariary"),
        Currency(code = "MKD", name = "Macedonian Denar"),
        Currency(code = "MMK", name = "Myanma Kyat"),
        Currency(code = "MNT", name = "Mongolian Tugrik"),
        Currency(code = "MOP", name = "Macanese Pataca"),
        Currency(code = "MRO", name = "Mauritanian Ouguiya"),
        Currency(code = "MUR", name = "Mauritian Rupee"),
        Currency(code = "MVR", name = "Maldivian Rufiyaa"),
        Currency(code = "MWK", name = "Malawian Kwacha"),
        Currency(code = "MXN", name = "Mexican Peso"),
        Currency(code = "MYR", name = "Malaysian Ringgit"),
        Currency(code = "MZN", name = "Mozambican Metical"),
        Currency(code = "NAD", name = "Namibian Dollar"),
        Currency(code = "NGN", name = "Nigerian Naira"),
        Currency(code = "NIO", name = "Nicaraguan C\u00f3rdoba"),
        Currency(code = "NOK", name = "Norwegian Krone"),
        Currency(code = "NPR", name = "Nepalese Rupee"),
        Currency(code = "NZD", name = "New Zealand Dollar"),
        Currency(code = "OMR", name = "Omani Rial"),
        Currency(code = "PAB", name = "Panamanian Balboa"),
        Currency(code = "PEN", name = "Peruvian Nuevo Sol"),
        Currency(code = "PGK", name = "Papua New Guinean Kina"),
        Currency(code = "PHP", name = "Philippine Peso"),
        Currency(code = "PKR", name = "Pakistani Rupee"),
        Currency(code = "PLN", name = "Polish Zloty"),
        Currency(code = "PYG", name = "Paraguayan Guarani"),
        Currency(code = "QAR", name = "Qatari Rial"),
        Currency(code = "RON", name = "Romanian Leu"),
        Currency(code = "RSD", name = "Serbian Dinar"),
        Currency(code = "RUB", name = "Russian Ruble"),
        Currency(code = "RWF", name = "Rwandan Franc"),
        Currency(code = "SAR", name = "Saudi Riyal"),
        Currency(code = "SBD", name = "Solomon Islands Dollar"),
        Currency(code = "SCR", name = "Seychellois Rupee"),
        Currency(code = "SDG", name = "Sudanese Pound"),
        Currency(code = "SEK", name = "Swedish Krona"),
        Currency(code = "SGD", name = "Singapore Dollar"),
        Currency(code = "SHP", name = "Saint Helena Pound"),
        Currency(code = "SLL", name = "Sierra Leonean Leone"),
        Currency(code = "SOS", name = "Somali Shilling"),
        Currency(code = "SRD", name = "Surinamese Dollar"),
        Currency(code = "STD", name = "S\u00e3o Tom\u00e9 and Pr\u00edncipe Dobra"),
        Currency(code = "SVC", name = "Salvadoran Col\u00f3n"),
        Currency(code = "SYP", name = "Syrian Pound"),
        Currency(code = "SZL", name = "Swazi Lilangeni"),
        Currency(code = "THB", name = "Thai Baht"),
        Currency(code = "TJS", name = "Tajikistani Somoni"),
        Currency(code = "TMT", name = "Turkmenistani Manat"),
        Currency(code = "TND", name = "Tunisian Dinar"),
        Currency(code = "TOP", name = "Tongan Pa\u02bbanga"),
        Currency(code = "TRY", name = "Turkish Lira"),
        Currency(code = "TTD", name = "Trinidad and Tobago Dollar"),
        Currency(code = "TWD", name = "New Taiwan Dollar"),
        Currency(code = "TZS", name = "Tanzanian Shilling"),
        Currency(code = "UAH", name = "Ukrainian Hryvnia"),
        Currency(code = "UGX", name = "Ugandan Shilling"),
        Currency(code = "USD", name = "United States Dollar"),
        Currency(code = "UYU", name = "Uruguayan Peso"),
        Currency(code = "UZS", name = "Uzbekistan Som"),
        Currency(code = "VEF", name = "Venezuelan Bol\u00edvar Fuerte"),
        Currency(code = "VND", name = "Vietnamese Dong"),
        Currency(code = "VUV", name = "Vanuatu Vatu"),
        Currency(code = "WST", name = "Samoan Tala"),
        Currency(code = "XAF", name = "CFA Franc BEAC"),
        Currency(code = "XAG", name = "Silver (troy ounce)"),
        Currency(code = "XAU", name = "Gold (troy ounce)"),
        Currency(code = "XCD", name = "East Caribbean Dollar"),
        Currency(code = "XDR", name = "Special Drawing Rights"),
        Currency(code = "XOF", name = "CFA Franc BCEAO"),
        Currency(code = "XPF", name = "CFP Franc"),
        Currency(code = "YER", name = "Yemeni Rial"),
        Currency(code = "ZAR", name = "South African Rand"),
        Currency(code = "ZMK", name = "Zambian Kwacha (pre-2013)"),
        Currency(code = "ZMW", name = "Zambian Kwacha"),
        Currency(code = "ZWL", name = "Zimbabwean Dollar")
    ).filter { currency ->
        currency.name.contains(searchCurrencyText.value, ignoreCase = true)
    }
}
