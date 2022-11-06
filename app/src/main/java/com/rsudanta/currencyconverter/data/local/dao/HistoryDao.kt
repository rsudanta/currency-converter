package com.rsudanta.currencyconverter.data.local.dao

import androidx.room.*
import com.rsudanta.currencyconverter.domain.model.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getHistories(): Flow<List<History>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistories()
}