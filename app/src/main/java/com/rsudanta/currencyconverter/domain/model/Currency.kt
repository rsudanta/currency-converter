package com.rsudanta.currencyconverter.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rsudanta.currencyconverter.util.Constant

data class Currency(
    val name: String,
    val code: String
)