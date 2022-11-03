package com.rsudanta.currencyconverter.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.rsudanta.currencyconverter.data.mapper.toConvert
import com.rsudanta.currencyconverter.data.remote.ExchangeRatesApi
import com.rsudanta.currencyconverter.data.remote.dto.ErrorResponse
import com.rsudanta.currencyconverter.domain.model.Convert
import com.rsudanta.currencyconverter.domain.model.Currency
import com.rsudanta.currencyconverter.domain.repository.ConversionRepository
import com.rsudanta.currencyconverter.util.Constant
import com.rsudanta.currencyconverter.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.PREFERENCE_NAME)

class ConversionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ExchangeRatesApi
) : ConversionRepository {
    override suspend fun getConversion(
        to: String?,
        from: String?,
        amount: Double
    ): Flow<Resource<Convert>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            try {
                val response = api.getConversion(to = to, from = from, amount = amount)
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()?.toConvert()))
                    emit(Resource.Loading(isLoading = false))
                } else {
                    val gson = Gson()
                    val errorResponse: ErrorResponse = gson.fromJson(
                        response.errorBody()!!.string(),
                        ErrorResponse::class.java
                    )
                    emit(Resource.Error(message = errorResponse.error.message))
                    emit(Resource.Loading(isLoading = false))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Unable to get response from server"))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    private object PreferenceKeys {
        val fromKey = stringPreferencesKey(name = Constant.FROM_KEY)
        val toKey = stringPreferencesKey(name = Constant.TO_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun persistConvertFromState(currency: Currency) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.fromKey] = currency.code
        }
    }

    override suspend fun persistConvertToState(currency: Currency) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.toKey] = currency.code
        }
    }

    override suspend fun readConvertFromState(): Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortState = preferences[PreferenceKeys.fromKey]
            sortState
        }

    override suspend fun readConvertToState(): Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortState = preferences[PreferenceKeys.toKey]
            sortState
        }
}