package com.example.movil_admin.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movil_admin.core.data.model.entities.Quote
import com.example.movil_admin.list.domain.ListQuoteUseCase
import com.example.movil_admin.list.domain.UpdateQuoteStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val listQuoteUseCase = ListQuoteUseCase()
    private val updateQuoteStatusUseCase = UpdateQuoteStatusUseCase()

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    suspend fun loadQuoteList() {
        if (_uiState.value.isFetching) return
        val result = listQuoteUseCase.invoke()
        if (result.isSuccess) {
            _uiState.value = _uiState.value.copy(
                quotations = result.getOrThrow().quotations
            )
            return
        }
    }

    fun resetFetched() {
        _uiState.value = _uiState.value.copy(
            isFetching = false
        )
    }

    fun updateQuoteStatus(status: String, id: Int) {
            Log.d("A", "AAAAAAA")
        viewModelScope.launch {
            try {
                val result = updateQuoteStatusUseCase.invoke(
                    status, id
                )

                if (result.isSuccess) {
                    val updatedQuotations = _uiState.value.quotations.map { quote ->
                        if (quote.id == id) {
                            quote.copy(status = status)
                        } else {
                            quote
                        }
                    }

                    _uiState.value = _uiState.value.copy(
                        quotations = updatedQuotations
                    )
                }

            } catch (e: Exception) {
                //:(
            }
        }

    }
}

data class ListUiState(
    val quotations: Collection<Quote> = emptyList(), val isFetching: Boolean = false
)