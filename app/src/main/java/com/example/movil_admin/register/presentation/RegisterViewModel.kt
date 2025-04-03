package com.example.movil_admin.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        validateForm()
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        validateForm()
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        validateForm()
    }

    private fun validateForm() {
        val currentState = _uiState.value
        val isEmailValid = currentState.email.isNotBlank() && currentState.email.contains("@")
        val isPasswordValid = currentState.password.length >= 6
        val doPasswordsMatch = currentState.password == currentState.confirmPassword

        _uiState.value = currentState.copy(
            isFormValid = isEmailValid && isPasswordValid && doPasswordsMatch
        )
    }

    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                // Simulate network call
                delay(1500)

                // For demo purposes, just check if email contains "error" to simulate failure
                if (_uiState.value.email.contains("error", ignoreCase = true)) {
                    throw Exception("Error al crear la cuenta. Inténtalo de nuevo.")
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRegistrationSuccessful = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido"
                )
            }
        }
    }
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false,
    val errorMessage: String? = null
)