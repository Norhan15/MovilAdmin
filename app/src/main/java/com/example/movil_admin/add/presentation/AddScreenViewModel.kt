package com.example.movil_admin.add.presentation

import androidx.lifecycle.ViewModel
import com.example.movil_admin.home.presentation.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddScreenViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    fun focusOnPack(){
        _uiState.value = _uiState.value.copy(
            isFocusOnPackForm = true
        )
    }

    fun focusOnExample(){
        _uiState.value = _uiState.value.copy(
            isFocusOnPackForm = false
        )
    }
}

data class AddUiState(
    val packName: String = "",
    val packDescription: String = "",
    val packPrice: Float = 0.0f,
    val packDetails: String = "",

    val exampleName:String = "",
    val exampleLink: String = "",

    val isFocusOnPackForm:Boolean = true
)