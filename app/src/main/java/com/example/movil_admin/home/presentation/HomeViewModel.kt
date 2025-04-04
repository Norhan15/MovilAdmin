package com.example.movil_admin.home.presentation

import androidx.lifecycle.ViewModel
import com.example.movil_admin.home.data.model.entities.Example
import com.example.movil_admin.home.data.model.entities.Pack
import com.example.movil_admin.home.domain.ListExamplesUseCase
import com.example.movil_admin.home.domain.ListPacksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val listPacksUseCase = ListPacksUseCase()
    private val listExamplesUseCase = ListExamplesUseCase()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    suspend fun loadPacks() {
        if (_uiState.value.isPackFetched) return;
        val result = listPacksUseCase.invoke()
        if (result.isSuccess) {
            _uiState.value = _uiState.value.copy(
                packs = result.getOrThrow().packs, isSuccess = true
            )
            return;
        }

        _uiState.value = _uiState.value.copy(
            isSuccess = false,
            errorMessage = "Ha ocurrido un error a la hora de recuperar los paquetes",
            isPackFetched = true
        )

    }

    suspend fun loadExamples() {
        if (_uiState.value.isExampleFetched) return;
        val result = listExamplesUseCase.invoke()
        if (result.isSuccess) {
            _uiState.value = _uiState.value.copy(
                examples = result.getOrThrow().examples, isSuccess = true
            )
            return;
        }

        _uiState.value = _uiState.value.copy(
            isSuccess = false,
            errorMessage = "Ha ocurrido un error a la hora de recuperar los ejemplos",
            isExampleFetched = true
        )

    }

    fun resetFetched() {
        _uiState.value = _uiState.value.copy(
            isPackFetched = false, isExampleFetched = false
        )
    }
}

data class HomeUiState(
    val packs: Collection<Pack> = emptyList(), val examples: Collection<Example> = emptyList(),


    val errorMessage: String? = "", val isSuccess: Boolean = false,

    val isExampleFetched: Boolean = false, val isPackFetched: Boolean = false
)