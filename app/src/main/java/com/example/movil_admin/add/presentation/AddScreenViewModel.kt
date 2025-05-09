package com.example.movil_admin.add.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movil_admin.add.domain.CreateExampleUseCase
import com.example.movil_admin.add.domain.CreatePackUseCase
import com.example.movil_admin.core.provider.MediaProvider
import com.example.movil_admin.home.presentation.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    val createPackUseCase = CreatePackUseCase()
    val createExampleUseCase = CreateExampleUseCase()

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

    fun updateImageUri(uri: Uri?) {
        _uiState.value = _uiState.value.copy(
            imageUri = uri,
            isLoading = false
        )
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(
            errorMessage = message,
            isLoading = false
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

    fun createNewExample(context: Context){
        viewModelScope.launch {
            try {
                val result = createExampleUseCase.invoke(
                    _uiState.value.exampleName,
                    _uiState.value.exampleLink,
                    _uiState.value.imageUri,
                    context
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

    val isSuccess: Boolean = false,

    val isLoading: Boolean = false,
    val imageUri: Uri? = null,
    val errorMessage: String? = null
)