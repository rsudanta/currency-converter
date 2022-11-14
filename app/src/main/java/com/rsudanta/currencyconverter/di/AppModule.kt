package com.rsudanta.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.rsudanta.currencyconverter.data.local.CurrencyConverterDatabase
import com.rsudanta.currencyconverter.data.local.dao.HistoryDao
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.repository.ConversionRepositoryImpl
import com.rsudanta.currencyconverter.data.repository.ExchangeRatesRepositoryImpl
import com.rsudanta.currencyconverter.data.repository.HistoryRepositoryImpl
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.domain.repository.ExchangeRatesRepository
import com.rsudanta.currencyconverter.domain.repository.HistoryRepository
import com.rsudanta.currencyconverter.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): CurrencyConverterDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyConverterDatabase::class.java,
            Constant.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideHistoryDao(database: CurrencyConverterDatabase): HistoryDao = database.historyDao


    @Provides
    @Singleton
    fun provideExchangeRatesApi(): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConversionRepository(
        @ApplicationContext context: Context,
        api: ExchangeRatesApi
    ): ConversionRepository {
        return ConversionRepositoryImpl(context = context, api = api)
    }

    @Provides
    @Singleton
    fun provideExchangeRatesRepository(
        api: ExchangeRatesApi
    ): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(historyDao: HistoryDao): HistoryRepository {
        return HistoryRepositoryImpl(dao = historyDao)
    }
}