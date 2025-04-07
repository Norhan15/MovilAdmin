package com.example.movil_admin.login.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movil_admin.login.domain.LoginUseCase
import com.example.movil_admin.register.presentation.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginUseCase = LoginUseCase()

    private val _uiState = MutableStateFlow(LoginUiStates())
    val uiState: StateFlow<LoginUiStates> = _uiState.asStateFlow()


    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            try {
                val result = loginUseCase.invoke(
                    _uiState.value.email, _uiState.value.password
                )

                if (result.isFailure) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "No se pudo completar el acceso", isSuccess = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = null, isSuccess = true
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No se pudo completar el acceso", isSuccess = false
                )
            }
        }
    }
}

data class LoginUiStates(
    val email: String = "",
    val password: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = "",
    val isSuccess: Boolean = false
)