package com.rsudanta.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rsudanta.currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    fun getCurrencies(): Flow<List<Currency>>

    @Query("SELECT * FROM currency where id=:id")
    fun getCurrencyById(id: Int): Flow<Currency>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currency: Currency)
}