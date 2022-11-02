package com.rsudanta.currencyconverter.data.repository

import com.rsudanta.currencyconverter.data.local.dao.HistoryDao
import com.rsudanta.currencyconverter.domain.model.History
import com.rsudanta.currencyconverter.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val dao: HistoryDao) :
    HistoryRepository {
    override suspend fun getHistories(): Flow<List<History>> {
        return dao.getHistories()
    }

    override suspend fun insertHistory(history: History) {
        dao.insertHistory(history = history)
    }

    override suspend fun deleteHistory(history: History) {
        dao.deleteHistoryById(history = history)
    }

}