package com.rsudanta.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.rsudanta.currencyconverter.data.local.CurrencyCallback
import com.rsudanta.currencyconverter.data.local.CurrencyConverterDatabase
import com.rsudanta.currencyconverter.data.local.dao.CurrencyDao
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.repository.ConversionRepositoryImpl
import com.rsudanta.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.domain.repository.CurrencyRepository
import com.rsudanta.currencyconverter.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        provider: Provider<CurrencyDao>
    ): CurrencyConverterDatabase {
        return Room.databaseBuilder(context, CurrencyConverterDatabase::class.java, Constant.DATABASE_NAME)
            .addCallback(CurrencyCallback(provider))
            .build()
    }


    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyConverterDatabase): CurrencyDao =
        database.currencyDao

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
    fun provideConversionRepository(api: ExchangeRatesApi): ConversionRepository {
        return ConversionRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(db: CurrencyConverterDatabase): CurrencyRepository {
        return CurrencyRepositoryImpl(db.currencyDao)
    }
}