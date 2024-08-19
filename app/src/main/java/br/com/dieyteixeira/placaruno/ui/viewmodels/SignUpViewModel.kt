package br.com.dieyteixeira.placaruno.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.dieyteixeira.placaruno.firebase.FirebaseAuthRepository
import br.com.dieyteixeira.placaruno.ui.states.SignUpUiState
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
        private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    private val _signUpIsSucessful = MutableSharedFlow<Boolean>()
    val signUpIsSucessful = _signUpIsSucessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { user ->
                    _uiState.update {
                        it.copy(email = user)
                    }
                },
                onPasswordChange = { password ->
                    val filteredPassword = password.filter { it.isLetterOrDigit() }
                    val passwordCharError = if (password != filteredPassword) {
                        "A senha não deve conter caracteres especiais\nApenas letras (A-Z) e números (0-9)"
                    } else {
                        null
                    }
                    _uiState.update {
                        it.copy(
                            password = password,
                            passwordCharError = passwordCharError
                        )
                    }
                },
                onConfirmPasswordChange = { confirmPassword ->
                    val passwordMismatchError = if (confirmPassword != _uiState.value.password) {
                        "As senhas não coincidem"
                    } else {
                        null
                    }
                    _uiState.update {
                        it.copy(
                            confirmPassword = confirmPassword,
                            passwordMismatchError = passwordMismatchError
                        )
                    }
                },
                onTogglePasswordVisibility = {
                    _uiState.update {
                        it.copy(isShowPassword = !_uiState.value.isShowPassword)
                    }
                }
            )
        }
    }

    suspend fun signUp() {
        try {
            if (_uiState.value.password.length < 6) {
                _uiState.update {
                    it.copy(
                        error = "A senha deve ter no mínimo 6 caracteres"
                    )
                }
                delay(3000)
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
                return
            }

            firebaseAuthRepository
                .signUp(
                    _uiState.value.email,
                    _uiState.value.password
                )
            firebaseAuthRepository.sendEmailVerification()
            _signUpIsSucessful.emit(true)
            _uiState.update {
                it.copy(
                    error = "Cadastro realizado com sucesso!\nVerifique seu e-mail para confirmar a conta."
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e) {
                is FirebaseAuthEmailException -> "Email inválido"
                else -> "Erro ao cadastrar usuário!"
            }

            Log.e("signInViewModel", "signIn: ", e)
            _uiState.update {
                it.copy(
                    error = errorMessage
                )
            }

            delay(3000)

            _uiState.update {
                it.copy(
                    error = null
                )
            }
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "signUp: ",e )
            _uiState.update {
                it.copy(
                    error = "Erro ao cadastrar usuário"
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }
}