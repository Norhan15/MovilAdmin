package com.example.movil_admin.add.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movil_admin.add.domain.CreatePackUseCase
import com.example.movil_admin.home.presentation.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    val createPackUseCase = CreatePackUseCase()

    fun focusOnPack() {
        _uiState.value = _uiState.value.copy(
            isFocusOnPackForm = true
        )
    }

    fun focusOnExample() {
        _uiState.value = _uiState.value.copy(
            isFocusOnPackForm = false
        )
    }

    fun onPackNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            packName = value
        )
    }

    fun onPackDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(
            packDescription = value
        )
    }

    fun onPackPriceChange(value: String) {
        if (value.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                packPrice = value
            )
        }
    }

    fun onPackDetailsChange(value: String) {
        _uiState.value = _uiState.value.copy(
            packDetails = value
        )
    }

    fun onExampleNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            exampleName = value
        )
    }

    fun onExampleLinkChange(value: String) {
        _uiState.value = _uiState.value.copy(
            exampleLink = value
        )
    }

    fun createNewPack() {
        viewModelScope.launch {
            try {
                val result = createPackUseCase.invoke(
                    _uiState.value.packName,
                    _uiState.value.packDescription,
                    _uiState.value.packDetails,
                    _uiState.value.packPrice.toFloat()
                )

                if (result.isFailure) {
                    _uiState.value = _uiState.value.copy(
                        isSuccess = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSuccess = true
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSuccess = false
                )
            }
        }
    }
}

data class AddUiState(
    val packName: String = "",
    val packDescription: String = "",
    val packPrice: String = "",
    val packDetails: String = "",

    val exampleName: String = "",
    val exampleLink: String = "",

    val isFocusOnPackForm: Boolean = true,

    val isSuccess: Boolean = false
)