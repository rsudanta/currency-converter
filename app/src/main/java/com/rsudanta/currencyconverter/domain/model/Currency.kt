package com.rsudanta.currencyconverter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rsudanta.currencyconverter.util.Constant

@Entity()
data class Currency(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val code: String
)