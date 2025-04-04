package com.example.movil_admin.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movil_admin.register.domain.CreateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    private val createUserUseCase = CreateUserUseCase()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun register() {
        viewModelScope.launch {
            try {
                val result = createUserUseCase.invoke(
                    _uiState.value.name, _uiState.value.email, _uiState.value.password
                )

                if (result.isFailure) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "No se pudo completar el registro", isSuccess = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = null, isSuccess = true
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No se pudo completar el registro", isSuccess = false
                )
            }
        }
    }

}

data class RegisterUiState(
    val name: String = "", val email: String = "", val password: String = "",
    val isLoading: Boolean = false, val errorMessage: String? = "", val isSuccess: Boolean = false
)