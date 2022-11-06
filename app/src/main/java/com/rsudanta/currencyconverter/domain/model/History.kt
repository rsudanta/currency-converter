package com.rsudanta.currencyconverter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey
    val id: Int? = null,
    val convertFrom: String,
    val convertTo: String,
    val amount: Double,
    val result: Double,
    val lastUpdate: Int,
    val createdAt: Int
)