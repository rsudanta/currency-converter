package com.rsudanta.currencyconverter.domain.repository

import com.rsudanta.currencyconverter.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun getHistories(): Flow<List<History>>

    suspend fun insertHistory(history: History)

    suspend fun deleteHistory(history: History)

    suspend fun deleteAllHistories()
}