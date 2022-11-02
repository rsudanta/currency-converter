package com.rsudanta.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rsudanta.currencyconverter.data.local.dao.HistoryDao
import com.rsudanta.currencyconverter.domain.model.History

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class CurrencyConverterDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao
}





