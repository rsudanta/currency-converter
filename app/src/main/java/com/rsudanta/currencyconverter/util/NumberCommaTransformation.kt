package com.rsudanta.currencyconverter.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.*

class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = AnnotatedString(text.text.toDoubleOrNull().formatWithComma()),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return text.text.toDoubleOrNull().formatWithComma().length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }
        )
    }
}

fun Double?.formatWithComma(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
    formatter.maximumFractionDigits = 4

    return formatter.format(this ?: 0)
}
