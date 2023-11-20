package com.example.monylover.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monylover.data.db.RoomDb
import com.example.monylover.models.Expense
import com.example.monylover.models.Recurrence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpansesState(
    val recurrence: Recurrence = Recurrence.None,
    val sumTotal: Double = 0.0,
    val expanses: List<Expense> = listOf()
)

class ExpansesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ExpansesState())
    val uiState : StateFlow<ExpansesState> = _uiState.asStateFlow()

    fun getExpenses(database: RoomDb) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {currentState ->
                currentState.copy(expanses = database.databaseDao().getAllExpenses())
            }
        }
    }
    fun setRecurrence(recurrence: Recurrence) {
        _uiState.update {
            it.copy(recurrence = recurrence)
        }
    }

}