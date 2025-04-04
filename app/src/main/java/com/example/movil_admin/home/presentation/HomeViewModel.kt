package com.example.movil_admin.home.presentation

import androidx.lifecycle.ViewModel
import com.example.movil_admin.home.data.model.entities.Example
import com.example.movil_admin.home.data.model.entities.Pack
import com.example.movil_admin.home.domain.ListExamplesUseCase
import com.example.movil_admin.home.domain.ListPacksUseCase
import com.example.movil_admin.home.domain.RemoveExampleUseCase
import com.example.movil_admin.home.domain.RemovePackUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val listPacksUseCase = ListPacksUseCase()
    private val listExamplesUseCase = ListExamplesUseCase()
    private val removePackUseCase = RemovePackUseCase()
    private val removeExamplesUseCase = RemoveExampleUseCase()

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

    suspend fun removePack(id: Int) {
        val result = removePackUseCase.invoke(id)
        if (result.isSuccess) {
            // Filtramos la colección actual para eliminar el pack con el ID especificado
            val updatedPacks = _uiState.value.packs.filter { it.id != id }

            _uiState.value = _uiState.value.copy(
                packs = updatedPacks, isSuccess = true, errorMessage = null
            )
            return
        } else {
            // Manejar el caso de error
            _uiState.value = _uiState.value.copy(
                isSuccess = false,
                errorMessage = result.exceptionOrNull()?.message ?: "Error al eliminar el paquete"
            )
        }
    }

    suspend fun removeExample(id: Int) {
        val result = removeExamplesUseCase.invoke(id)
        if (result.isSuccess) {
            // Filtramos la colección actual para eliminar el ejemplo con el ID especificado
            val updatedExamples = _uiState.value.examples.filter { it.id != id }

            _uiState.value = _uiState.value.copy(
                examples = updatedExamples, isSuccess = true, errorMessage = null
            )
            return
        } else {
            // Manejar el caso de error
            _uiState.value = _uiState.value.copy(
                isSuccess = false,
                errorMessage = result.exceptionOrNull()?.message ?: "Error al eliminar el ejemplo"
            )
        }
    }
}

data class HomeUiState(
    val packs: Collection<Pack> = emptyList(), val examples: Collection<Example> = emptyList(),


    val errorMessage: String? = "", val isSuccess: Boolean = false,
    val isExampleFetched: Boolean = false, val isPackFetched: Boolean = false
)