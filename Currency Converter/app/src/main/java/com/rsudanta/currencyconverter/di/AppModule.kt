package com.rsudanta.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.rsudanta.currencyconverter.data.local.CurrencyConverterDatabase
import com.rsudanta.currencyconverter.data.local.dao.HistoryDao
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesAdapter
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.repository.ConversionRepositoryImpl
import com.rsudanta.currencyconverter.data.repository.ExchangeRatesRepositoryImpl
import com.rsudanta.currencyconverter.data.repository.HistoryRepositoryImpl
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.domain.repository.ExchangeRatesRepository
import com.rsudanta.currencyconverter.domain.repository.HistoryRepository
import com.rsudanta.currencyconverter.presentation.SharedViewModel
import com.rsudanta.currencyconverter.util.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(ExchangeRatesAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideExchangeRatesApi(moshi: Moshi): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConversionRepository(
        @ApplicationContext context: Context,
        api: ExchangeRatesApi,
        moshi: Moshi
    ): ConversionRepository {
        return ConversionRepositoryImpl(context = context, api = api, moshi = moshi)
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

    @Provides
    @Singleton
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }
}