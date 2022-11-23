package com.rsudanta.currencyconverter.presentation.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsudanta.currencyconverter.domain.model.History
import com.rsudanta.currencyconverter.domain.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val repository: HistoryRepository) : ViewModel() {
    var historyState = mutableStateOf(HistoryState())
        private set

    fun getHistories() {
        historyState.value = historyState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                repository.getHistories().collect { histories ->
                    historyState.value =
                        historyState.value.copy(histories = histories, isLoading = false)
                    historyState.value.histories =
                        historyState.value.histories.sortedByDescending { it.createdAt }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteAllHistories() {
        viewModelScope.launch {
            repository.deleteAllHistories()
        }
    }

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            repository.deleteHistory(history)
        }
    }
}